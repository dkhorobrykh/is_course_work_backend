package ru.itmo.is.course_work.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.*;
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
    private final ShipTypeRepository shipTypeRepository;
    private final ServiceClassRepository serviceClassRepository;

    @Transactional
    public ShipDto addNewShipWithStatus(ShipDto shipDto) {
        Ship ship = new Ship();
        ship.setName(shipDto.getName());
        ship.setNumber(shipDto.getNumber());
        ship.setRegistrationDatetime(shipDto.getRegistrationDatetime());
        ship.setPhoto(shipDto.getPhoto());

        if (shipDto.getShipTypeId() != null) {
            ship.setShipType(getShipTypeById(shipDto.getShipTypeId()));
        }

        Set<ServiceClass> serviceClasses = shipDto.getServiceClassIds().stream()
                .map(id -> serviceClassRepository.findById(id)
                        .orElseThrow(() -> new CustomException(ExceptionEnum.SERVICE_CLASS_NOT_FOUND)))
                .collect(Collectors.toSet());
        ship.setServiceClasses(serviceClasses);

        ShipStatus shipStatus = new ShipStatus();
        shipStatus.setShip(ship);
        shipStatus.setFuelLevel(100.0);
        shipStatus.setFuelStatus(ShipStatus.FuelStatus.FULL);
        shipStatus.setEngineStatus(ShipStatus.EngineStatus.OK);
        shipStatus.setLastUpdated(Instant.now());

        ship.setShipStatus(shipStatus);

        Ship savedShip = shipRepository.save(ship);

        return new ShipDto(
                savedShip.getId(),
                savedShip.getName(),
                savedShip.getShipType() != null ? savedShip.getShipType().getId() : null,
                savedShip.getNumber(),
                savedShip.getRegistrationDatetime(),
                savedShip.getPhoto(),
                savedShip.getServiceClasses().stream()
                        .map(ServiceClass::getId)
                        .collect(Collectors.toSet())
        );
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
}
