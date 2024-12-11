package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Cargo;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.repository.FlightRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

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
}
