package ru.itmo.is.course_work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.dto.FlightReportDto;
import ru.itmo.is.course_work.repository.FlightRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightReportService {

    @Autowired
    private FlightRepository flightRepository;

    public List<FlightReportDto> generateCompletedFlightsReport() {
        List<Flight> completedFlights = flightRepository.findByFlightStatusName("COMPLETED");
        return completedFlights.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FlightReportDto convertToDTO(Flight flight) {
        return new FlightReportDto(
                flight.getId(),
                flight.getName(),
                flight.getDepartureDatetime(),
                flight.getArrivalDatetime(),
                flight.getFlightStatus().getName(),
                flight.getCargoStatus().getName(),
                flight.getTotalSeats(),
                flight.getBookedSeats()
        );
    }
}
