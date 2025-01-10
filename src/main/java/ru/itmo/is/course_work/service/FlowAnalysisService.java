package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.model.Cargo;
import ru.itmo.is.course_work.model.Passenger;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.dto.FlightAnalysisDto;
import ru.itmo.is.course_work.repository.CargoRepository;
import ru.itmo.is.course_work.repository.FlightRepository;
import ru.itmo.is.course_work.repository.PassengerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowAnalysisService {

    private final PassengerRepository passengerRepository;
    private final CargoRepository cargoRepository;
    private final FlightRepository flightRepository;

    public List<FlightAnalysisDto> analyzeFlow() {
        List<Flight> flights = flightRepository.findAllByOrderById();
        return flights.stream().map(flight -> {
            List<Passenger> passengers = passengerRepository.findAllByFlight_Id(flight.getId());
            List<Cargo> cargos = cargoRepository.findAllByFlight_IdOrderById(flight.getId());
            return new FlightAnalysisDto(flight, passengers, cargos);
        }).collect(Collectors.toList());
    }
}
