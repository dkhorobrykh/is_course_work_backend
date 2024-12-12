package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.FlightStatus;
import ru.itmo.is.course_work.repository.FlightStatusRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightStatusService {
    private final FlightStatusRepository flightStatusRepository;

    public FlightStatus getFlightStatusByName(String name) {
        return flightStatusRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new CustomException(ExceptionEnum.FLIGHT_STATUS_NOT_FOUND));
    }
}
