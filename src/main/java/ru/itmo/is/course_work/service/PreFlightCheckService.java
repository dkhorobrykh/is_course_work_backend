package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.Planet;
import ru.itmo.is.course_work.model.ShipStatus;
import ru.itmo.is.course_work.repository.FlightScheduleRepository;
import ru.itmo.is.course_work.repository.ShipStatusRepository;

@Service
@RequiredArgsConstructor
public class PreFlightCheckService {

    private final ShipStatusService shipStatusService;
    private final FlightScheduleService flightScheduleService;
    private final FlightScheduleRepository flightScheduleRepository;
    private final ShipStatusRepository shipStatusRepository;

    private boolean protectionAdded = false;

    public boolean performPreFlightChecks(Long scheduleId) {
        FlightSchedule schedule = flightScheduleService.getScheduleById(scheduleId);
        var flight = flightScheduleService.getFlightByScheduleId(scheduleId);
        ShipStatus shipStatus = shipStatusService.getShipStatus(flight.getShip().getId());

        if (shipStatus.getFuelStatus() == ShipStatus.FuelStatus.CRITICAL) {
            throw new CustomException(ExceptionEnum.PREFLIGHT_CHECK_FAILED_FUEL);
        } else if (shipStatus.getFuelStatus() == ShipStatus.FuelStatus.LOW) {
            throw new CustomException(ExceptionEnum.PREFLIGHT_CHECK_FAILED_FUEL);
        }

        if (shipStatus.getEngineStatus() == ShipStatus.EngineStatus.CRITICAL) {
            throw new CustomException(ExceptionEnum.PREFLIGHT_CHECK_FAILED_ENGINE);
        } else if (shipStatus.getEngineStatus() == ShipStatus.EngineStatus.WARNING) {
            throw new CustomException(ExceptionEnum.PREFLIGHT_CHECK_FAILED_ENGINE);
        }

        Planet departurePlanet = schedule.getPlanetDeparture();
        Planet arrivalPlanet = schedule.getPlanetArrival();

        if (!departurePlanet.getGalaxy().equals(arrivalPlanet.getGalaxy())) {
            ensureProtectionFromAnomaliesAndRadiation(shipStatus);
            protectionAdded = true;
        }

        return true;
    }

    private void ensureProtectionFromAnomaliesAndRadiation(ShipStatus shipStatus) {
        shipStatus.setRadiationResistance(ShipStatus.RadiationResistance.RESISTANT);
        shipStatusRepository.save(shipStatus);
    }

    public boolean isProtectionAdded() {
        return protectionAdded;
    }

    public void resetProtectionAdded() {
        protectionAdded = false;
    }
}
