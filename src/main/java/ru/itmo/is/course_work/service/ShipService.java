package ru.itmo.is.course_work.service;

import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.*;
import ru.itmo.is.course_work.model.dto.ShipAddDto;
import ru.itmo.is.course_work.model.dto.ShipDto;
import ru.itmo.is.course_work.repository.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipService {

    private final ShipRepository shipRepository;
    private final CargoRepository cargoRepository;
    private final ShipTypeRepository shipTypeRepository;
    private final ServiceClassRepository serviceClassRepository;
    private final ServiceClassService serviceClassService;
    private final HabitatService habitatService;
    private final TemperatureTypeService temperatureTypeService;
    private final AirService airService;

    public List<Ship> getAll() {
        return shipRepository.findAll();
    }

    @Transactional
    public Ship addNewShipWithStatus(ShipAddDto shipDto) {
        Ship ship = new Ship();
        ship.setName(shipDto.getName());
        ship.setNumber(shipDto.getNumber());
        ship.setRegistrationDatetime(Instant.now());
        ship.setPhoto(shipDto.getPhoto());

//        if (shipDto.getShipTypeId() != null)
//            ship.setShipType(getShipTypeById(shipDto.getShipTypeId()));

        Set<ServiceClass> serviceClasses = shipDto.getServiceClasses().stream().map(sc -> {
            var newServiceClass = ServiceClass.builder()

                .name(sc.getName())
                .cost(sc.getCost())
                .outputName(sc.getName())

                .build();

            return serviceClassRepository.saveAndFlush(newServiceClass);
        }).collect(Collectors.toSet());

        ship.setServiceClasses(serviceClasses);

        ship.setHabitat(habitatService.getByName(shipDto.getHabitat().getName()));
        ship.setTemperatureType(temperatureTypeService.getByName(shipDto.getTemperatureType().getName()));
        ship.setAirType(airService.getByName(shipDto.getAirType().getName()));

        ship.setPassengerCapacity(shipDto.getPassengerCapacity());

        ship.setAtmosphereType(shipDto.getAirType().getName());

        ShipStatus shipStatus = new ShipStatus();
        shipStatus.setShip(ship);
        shipStatus.setFuelLevel(100.0);
        shipStatus.setFuelStatus(ShipStatus.FuelStatus.FULL);
        shipStatus.setEngineStatus(ShipStatus.EngineStatus.OK);
        shipStatus.setLastUpdated(Instant.now());

        ship.setShipStatus(shipStatus);

        return shipRepository.save(ship);
    }

    public List<Ship> findShipsForPassenger(UserData userData) {
        PhysiologicalType physiologicalType = userData.getPhysiologicalType();
        TemperatureType temperatureType = physiologicalType.getTemperatureType();
        Habitat habitat = physiologicalType.getHabitat();
        AirType airType = physiologicalType.getAirType();

        if (temperatureType.getMinTemperature() > 20 && temperatureType.getMaxTemperature() < 30) {
            return shipRepository.findByServiceClasses_NameIn(List.of("PREMIUM", "VIP"));
        }

        if ("Earth-like".equalsIgnoreCase(habitat.getName()) && "Oxygen".equalsIgnoreCase(airType.getName())) {
            return shipRepository.findByServiceClasses_NameIn(List.of("VIP", "PREMIUM"));
        }

        if ("Desert".equalsIgnoreCase(habitat.getName()) && "Nitrogen".equalsIgnoreCase(airType.getName())) {
            return shipRepository.findByServiceClasses_NameIn(List.of("PREMIUM"));
        }

        if ("Oceanic".equalsIgnoreCase(habitat.getName()) && "Carbon Dioxide".equalsIgnoreCase(airType.getName())) {
            return shipRepository.findByServiceClasses_NameIn(List.of("VIP"));
        }

        return shipRepository.findAll();
    }

    public ShipType getShipTypeById(Long id) {
        return shipTypeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.SHIP_TYPE_NOT_FOUND));
    }

    @Transactional
    public boolean updateShipConditions(Long shipId, double temperature, String atmosphereType, double radiationProtectionLevel) {
        Ship ship = shipRepository.findById(shipId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.SHIP_NOT_FOUND));

        List<Cargo> cargos = cargoRepository.findByShipId(shipId);

        for (Cargo cargo : cargos) {
            CargoCondition cargoCondition = cargo.getCargoCondition();

            if (!matchesConditions(temperature, atmosphereType, radiationProtectionLevel, cargoCondition)) {
                log.warn("Условия не соответствуют требованиям груза {}", cargo.getName());
                return false;
            }
        }

        List<Passenger> passengers = findPassengersByShip(ship);

        for (Passenger passenger : passengers) {
            PhysiologicalType physiologicalType = passenger.getUser().getPhysiologicalType();

            if (!matchesConditions(temperature, atmosphereType, radiationProtectionLevel, physiologicalType)) {
                log.warn("Условия не соответствуют требованиям пассажира {}", passenger.getUser().getFirstName());
                return false;
            }
        }

        ship.setTemperature(temperature);
        ship.setAtmosphereType(atmosphereType);
        ship.setRadiationProtectionLevel(radiationProtectionLevel);
        shipRepository.save(ship);
        log.info("Условия на борту корабля {} успешно обновлены", ship.getName());
        return true;
    }

    private boolean matchesConditions(double temperature, String atmosphereType, double radiationProtectionLevel, CargoCondition cargoCondition) {
        return atmosphereType.equals(cargoCondition.getAirType().getName()) &&
                temperature >= cargoCondition.getTemperatureType().getMinTemperature() &&
                temperature <= cargoCondition.getTemperatureType().getMaxTemperature();
    }

    private boolean matchesConditions(double temperature, String atmosphereType, double radiationProtectionLevel, PhysiologicalType physiologicalType) {
        return atmosphereType.equals(physiologicalType.getAirType().getName()) &&
                temperature >= physiologicalType.getTemperatureType().getMinTemperature() &&
                temperature <= physiologicalType.getTemperatureType().getMaxTemperature();
    }

    private List<Passenger> findPassengersByShip(Ship ship) {
        return List.of();
    }

    public Ship getById(Long id) {
        return shipRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.SHIP_NOT_FOUND));
    }

    public Ship update(Long shipId, ShipDto shipDto) {
        Ship ship = getById(shipId);
        ship.setName(shipDto.getName());
        ship.setNumber(shipDto.getNumber());
        ship.setPhoto(shipDto.getPhoto());

        var newServiceClasses = new HashSet<ServiceClass>();

        for (var serviceClassDto : shipDto.getServiceClasses()) {
            if (serviceClassDto.getId() != null) {
                var serviceClass = serviceClassService.getServiceClassById(serviceClassDto.getId());
                serviceClass.setName(serviceClassDto.getName());
                serviceClass.setCost(serviceClassDto.getCost());
                serviceClass.setOutputName(serviceClassDto.getName());
                newServiceClasses.add(serviceClassRepository.saveAndFlush(serviceClass));
            } else {
                var newServiceClass = ServiceClass.builder()
                        .name(serviceClassDto.getName())
                        .cost(serviceClassDto.getCost())
                        .outputName(serviceClassDto.getName())
                        .build();
                newServiceClasses.add(serviceClassRepository.saveAndFlush(newServiceClass));
            }
        }

        ship.setServiceClasses(newServiceClasses);
        return shipRepository.save(ship);
    }

}
