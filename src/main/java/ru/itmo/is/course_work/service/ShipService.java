package ru.itmo.is.course_work.service;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.ServiceClass;
import ru.itmo.is.course_work.model.Ship;
import ru.itmo.is.course_work.model.ShipStatus;
import ru.itmo.is.course_work.model.dto.ShipDto;
import ru.itmo.is.course_work.repository.ShipRepository;
import ru.itmo.is.course_work.repository.ShipStatusRepository;
import ru.itmo.is.course_work.repository.ServiceClassRepository;
import ru.itmo.is.course_work.repository.ShipTypeRepository;
import ru.itmo.is.course_work.model.dto.ShipTypeDto;
import ru.itmo.is.course_work.model.dto.ServiceClassDto;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final ShipStatusRepository shipStatusRepository;
    private final ShipTypeRepository shipTypeRepository;
    private final ServiceClassRepository serviceClassRepository;

    @Autowired
    public ShipService(ShipRepository shipRepository, ShipStatusRepository shipStatusRepository, ShipTypeRepository shipTypeRepository, ServiceClassRepository serviceClassRepository) {
        this.shipRepository = shipRepository;
        this.shipStatusRepository = shipStatusRepository;
        this.shipTypeRepository = shipTypeRepository;
        this.serviceClassRepository = serviceClassRepository;
    }

    @Transactional
    public ShipDto addNewShipWithStatus(ShipDto shipDto) {
        Ship ship = new Ship();
        ship.setName(shipDto.getName());
        ship.setNumber(shipDto.getNumber());
        ship.setRegistrationDatetime(shipDto.getRegistrationDatetime());
        ship.setPhoto(shipDto.getPhoto());

        if (shipDto.getShipTypeId() != null) {
            ship.setShipType(shipTypeRepository.findById(shipDto.getShipTypeId())
                    .orElseThrow(() -> new CustomException(ExceptionEnum.SHIP_TYPE_NOT_FOUND)));
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
}

