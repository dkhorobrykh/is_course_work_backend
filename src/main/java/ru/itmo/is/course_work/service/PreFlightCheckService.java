package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.ShipStatus;
import ru.itmo.is.course_work.model.UserData;
import ru.itmo.is.course_work.repository.FlightScheduleRepository;
import ru.itmo.is.course_work.repository.ShipStatusRepository;

@Service
@RequiredArgsConstructor
public class PreFlightCheckService {

    private final ShipStatusService shipStatusService;
    private final FlightScheduleService flightScheduleService;
    private final FlightScheduleRepository flightScheduleRepository;

    public boolean performPreFlightChecks(Long scheduleId) {
        FlightSchedule schedule = flightScheduleService.getScheduleById(scheduleId);
        ShipStatus shipStatus = shipStatusService.getShipStatus(schedule.getFlight().getShip().getId());

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

        return true;
    }


}
