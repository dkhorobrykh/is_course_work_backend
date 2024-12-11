package ru.itmo.is.course_work.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Cargo;
import ru.itmo.is.course_work.model.CargoAddDto;
import ru.itmo.is.course_work.model.CargoCondition;
import ru.itmo.is.course_work.repository.CargoConditionRepository;
import ru.itmo.is.course_work.repository.CargoRepository;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;
    private final CargoConditionRepository cargoConditionRepository;
    private final UserService userService;
    private final NatureService natureService;
    private final FlightService flightService;

    public Cargo getCargoById(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.CARGO_NOT_FOUND));
    }

    public List<Cargo> getAllWhereCurrentUserIsSender() {
        var currentUser = RoleService.getCurrentUser();
        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        return cargoRepository.findAllBySender_Id(currentUser.getId());
    }

    public List<Cargo> getAllWhereCurrentUserIsRecipient() {
        var currentUser = RoleService.getCurrentUser();
        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        return cargoRepository.findAllByRecipient_Id(currentUser.getId());
    }

    public List<Cargo> getAllByFlightId(Long flightId) {
        return cargoRepository.findAllByFlight_Id(flightId);
    }

    public List<Cargo> getAllCargo() {
        return cargoRepository.findAll();
    }

    public Cargo addCargo(@Valid CargoAddDto dto) {
        var currentUser = RoleService.getCurrentUser();
        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        var recipient = userService.getById(dto.getRecipientId());

        var weight = dto.getWeight();

        var randomName = generateRandomName();

        var airType = natureService.getAirTypeById(dto.getCargoCondition().getAirTypeId());

        var habitat = natureService.getHabitatById(dto.getCargoCondition().getHabitatId());

        var temperatureType = natureService.getTemperatureTypeById(dto.getCargoCondition().getTemperatureTypeId());

        var cargoCondition = CargoCondition.builder()

                .airType(airType)
                .habitat(habitat)
                .temperatureType(temperatureType)

                .build();

        cargoCondition = cargoConditionRepository.saveAndFlush(cargoCondition);

        var newCargo = Cargo.builder()

                .name(randomName)
                .sender(currentUser)
                .recipient(recipient)
                .weight(weight)
                .cargoCondition(cargoCondition)

                .build();

        return cargoRepository.saveAndFlush(newCargo);
    }

    private static String generateRandomName() {
        final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final Random RANDOM = new Random();

        StringBuilder sb = new StringBuilder();

        // Generate 3 random letters
        for (int i = 0; i < 3; i++) {
            sb.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        }

        sb.append('-');

        // Generate 5 random digits
        for (int i = 0; i < 5; i++) {
            sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        }

        return sb.toString();
    }

    @Scheduled(fixedDelayString = "${task.assignCargoToFlightSeconds}", timeUnit = TimeUnit.SECONDS)
    public void assignCargoToFlightTask() {
        log.debug("Начало назначения грузов на рейсы");

        var freeCargos = cargoRepository.findAllByFlightIsNull();

        for (var cargo : freeCargos) {
            var flight = flightService.findSuitableFlightForCargo(cargo);

            if (flight != null) {
                cargo.setFlight(flight);
                log.info("Груз с номером [{}] и id [{}] назначен на рейс с номером [{}] и id [{}]", cargo.getName(), cargo.getId(), flight.getName(), flight.getId());
                cargoRepository.saveAndFlush(cargo);
            }

        }

        log.debug("Окончание назначения грузов на рейсы");
    }

}
