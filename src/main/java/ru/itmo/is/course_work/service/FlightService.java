package ru.itmo.is.course_work.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Cargo;
import ru.itmo.is.course_work.model.CargoStatus;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.FlightStatus;
import ru.itmo.is.course_work.model.dto.ChangeStatusDto;
import ru.itmo.is.course_work.repository.FlightRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final PlanetService planetService;
    private final FlightStatusService flightStatusService;
    private final CargoStatusService cargoStatusService;

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.FLIGHT_NOT_FOUND));
    }

    public List<Flight> getAllAvailableFlights() {
        // todo: только с нужным статусом
        return flightRepository.findAll();
    }

    public Flight findSuitableFlightForCargo(Cargo cargo) {
        var conditions = cargo.getCargoCondition();

        for (var flight: getAllAvailableFlights()) {
            var ship = flight.getShip();

            if (!ship.getTemperatureType().getId().equals(conditions.getTemperatureType().getId()))
                continue;

            if (!ship.getHabitat().getId().equals(conditions.getHabitat().getId()))
                continue;

            if (!ship.getAirType().getId().equals(conditions.getAirType().getId()))
                continue;

            return flight;
        }

        return null;
    }

    public List<Flight> selectAvailableFlights(Long departurePlanetId, Long arrivalPlanetId) {
        var departure = planetService.getPlanetById(departurePlanetId);
        var arrival = planetService.getPlanetById(arrivalPlanetId);

        var currentUser = RoleService.getCurrentUser();
        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        var type = currentUser.getPhysiologicalType();

        return flightRepository.findAllAvailableForUser(departure.getId(), arrival.getId(), type == null ? null : type.getAirType().getId(), type == null ? null : type.getHabitat().getId(), type == null ? null : type.getTemperatureType().getId());
    }

    public Flight changeStatus(Long flightId, ChangeStatusDto dto) {
        var newStatus = flightStatusService.getFlightStatusByName(dto.getNewStatus());

        var flight = getFlightById(flightId);

        var oldStatus = flight.getFlightStatus();

        switch (newStatus.getName()) {
            case FlightStatus.APPROVED -> {
                if (!flight.getFlightStatus().getName().equals(FlightStatus.PLANNED))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);
            }

            case FlightStatus.REGISTRATION -> {
                if (!oldStatus.getName().equals(FlightStatus.APPROVED))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);
            }

            case FlightStatus.BOARDING -> {
                if (!oldStatus.getName().equals(FlightStatus.REGISTRATION))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);
            }

            case FlightStatus.FLIGHT -> {
                if (!oldStatus.getName().equals(FlightStatus.BOARDING))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);

                if (!flight.getCargoStatus().getName().equals(CargoStatus.READY))
                    throw new CustomException(ExceptionEnum.CARGO_IS_NOT_READY);
            }

            case FlightStatus.DISEMBARKATION -> {
                if (!oldStatus.getName().equals(FlightStatus.FLIGHT))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);
            }

            case FlightStatus.COMPLETED -> {
                if (!oldStatus.getName().equals(FlightStatus.DISEMBARKATION))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);

                if (!flight.getCargoStatus().getName().equals(CargoStatus.COMPLETED))
                    throw new CustomException(ExceptionEnum.CARGO_IS_NOT_COMPLETED);
            }
        }

        flight.setFlightStatus(newStatus);

        log.info("Статус рейса [{}] изменен: {} -> {}", flight.getId(), oldStatus.getOutputName(), newStatus.getOutputName());

        return flightRepository.saveAndFlush(flight);
    }

    public Flight changeCargoStatus(Long flightId, @Valid ChangeStatusDto dto) {
        var newStatus = cargoStatusService.getCargoStatusByName(dto.getNewStatus());

        var flight = getFlightById(flightId);

        var oldStatus = flight.getCargoStatus();

        switch (newStatus.getName()) {
            case (CargoStatus.CUSTOMS_CHECK) -> {
                if (!oldStatus.getName().equals(CargoStatus.WAITING_START))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);
            }

            case (CargoStatus.LOADING) -> {
                if (!oldStatus.getName().equals(CargoStatus.CUSTOMS_CHECK))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);
            }

            case (CargoStatus.READY) -> {
                if (!oldStatus.getName().equals(CargoStatus.LOADING))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);
            }

            case (CargoStatus.UNLOADING) -> {
                if (!oldStatus.getName().equals(CargoStatus.READY))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);

                if (flight.getFlightStatus().getName().equals(FlightStatus.FLIGHT))
                    throw new CustomException(ExceptionEnum.FLIGHT_SHOULD_BE_LANDED_BEFORE_UNLOADING_CARGO);
            }

            case (CargoStatus.COMPLETED) -> {
                if (!oldStatus.getName().equals(CargoStatus.UNLOADING))
                    throw new CustomException(ExceptionEnum.WRONG_STATUS_SWITCHING);
            }
        }

        flight.setCargoStatus(newStatus);

        log.info("Статус груза рейса [{}] изменен: {} -> {}", flight.getId(), oldStatus.getOutputName(), newStatus.getOutputName());

        return flightRepository.saveAndFlush(flight);
    }
}
