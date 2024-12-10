package ru.itmo.is.course_work.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.dto.FlightScheduleRequest;
import ru.itmo.is.course_work.repository.FlightRepository;
import ru.itmo.is.course_work.repository.FlightScheduleRepository;
import ru.itmo.is.course_work.repository.PlanetRepository;
import ru.itmo.is.course_work.repository.ScheduleStatusRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FlightScheduleService {

    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;
    private final PlanetRepository planetRepository;
    private final ScheduleStatusRepository scheduleStatusRepository;

    public List<FlightSchedule> getAllSchedules() {
        return flightScheduleRepository.findAll();
    }

    public FlightSchedule getScheduleById(Long id) {
        return flightScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @Transactional
    public FlightSchedule createSchedule(FlightScheduleRequest request) {
        FlightSchedule schedule = new FlightSchedule();
        schedule.setFlight(flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found")));
        schedule.setPlanetDeparture(planetRepository.findById(request.getPlanetDepartureId())
                .orElseThrow(() -> new RuntimeException("Planet departure not found")));
        schedule.setPlanetArrival(planetRepository.findById(request.getPlanetArrivalId())
                .orElseThrow(() -> new RuntimeException("Planet arrival not found")));
        schedule.setDepartureDatetime(request.getDepartureDatetime());
        schedule.setArrivalDatetime(request.getArrivalDatetime());
        schedule.setScheduleStatus(scheduleStatusRepository.findById(request.getScheduleStatusId())
                .orElseThrow(() -> new RuntimeException("Schedule status not found")));

        return flightScheduleRepository.save(schedule);
    }

    @Transactional
    public FlightSchedule updateSchedule(Long id, FlightScheduleRequest request) {
        FlightSchedule schedule = getScheduleById(id);
        schedule.setFlight(flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found")));
        schedule.setPlanetDeparture(planetRepository.findById(request.getPlanetDepartureId())
                .orElseThrow(() -> new RuntimeException("Planet departure not found")));
        schedule.setPlanetArrival(planetRepository.findById(request.getPlanetArrivalId())
                .orElseThrow(() -> new RuntimeException("Planet arrival not found")));
        schedule.setDepartureDatetime(request.getDepartureDatetime());
        schedule.setArrivalDatetime(request.getArrivalDatetime());
        schedule.setScheduleStatus(scheduleStatusRepository.findById(request.getScheduleStatusId())
                .orElseThrow(() -> new RuntimeException("Schedule status not found")));

        return flightScheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        FlightSchedule schedule = getScheduleById(id);
        flightScheduleRepository.delete(schedule);
    }
}
