package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.ShipStatus;
import ru.itmo.is.course_work.repository.ShipStatusRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ShipStatusService {
    private final ShipStatusRepository shipStatusRepository;

    public ShipStatus getShipStatus(Long shipId) {
        return shipStatusRepository.findByShipId(shipId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.SHIP_STATUS_NOT_FOUND));
    }

    public ShipStatus updateShipStatus(Long shipId, Double fuelLevel) {
        ShipStatus shipStatus = shipStatusRepository.findByShipId(shipId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.SHIP_STATUS_NOT_FOUND));

        shipStatus.setFuelLevel(fuelLevel);
        shipStatus.setFuelStatus(determineFuelStatus(fuelLevel));
        shipStatus.setEngineStatus(determineEngineStatus(fuelLevel));

        shipStatus.setLastUpdated(Instant.now());

        if (fuelLevel < 10.0) {
            System.out.println("ALERT: Fuel level critically low for ship ID: " + shipId);
        }

        if (shipStatus.getEngineStatus() == ShipStatus.EngineStatus.CRITICAL) {
            System.out.println("ALERT: Engine status critical for ship ID: " + shipId);
        }

        return shipStatusRepository.save(shipStatus);
    }

    private ShipStatus.EngineStatus determineEngineStatus(Double fuelLevel) {
        if (fuelLevel > 75.0) {
            return ShipStatus.EngineStatus.OK;
        } else if (fuelLevel > 25.0) {
            return ShipStatus.EngineStatus.WARNING;
        } else {
            return ShipStatus.EngineStatus.CRITICAL;
        }
    }

    private ShipStatus.FuelStatus determineFuelStatus(Double fuelLevel) {
        if (fuelLevel > 75.0) {
            return ShipStatus.FuelStatus.FULL;
        } else if (fuelLevel > 50.0) {
            return ShipStatus.FuelStatus.HIGH;
        } else if (fuelLevel > 25.0) {
            return ShipStatus.FuelStatus.MEDIUM;
        } else if (fuelLevel > 10.0) {
            return ShipStatus.FuelStatus.LOW;
        } else {
            return ShipStatus.FuelStatus.CRITICAL;
        }
    }
}
