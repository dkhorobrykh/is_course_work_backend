package ru.itmo.is.course_work.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.model.CargoStatus;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.FlightStatus;
import ru.itmo.is.course_work.model.dto.AssignFlightToScheduleDto;
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
    private final PlanetService planetService;
    private final ShipService shipService;
    private final FlightStatusService flightStatusService;
    private final CargoStatusService cargoStatusService;

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
        schedule.setPlanetDeparture(planetService.getPlanetByName(request.getPlanetDeparture()));
        schedule.setPlanetArrival(planetService.getPlanetByName(request.getPlanetArrival()));
        schedule.setDepartureDatetime(request.getDepartureDatetime());
        schedule.setArrivalDatetime(request.getArrivalDatetime());


        return flightScheduleRepository.save(schedule);
    }

    @Transactional
    public FlightSchedule updateSchedule(Long id, FlightScheduleRequest request) {
        FlightSchedule schedule = getScheduleById(id);
        schedule.setPlanetDeparture(planetService.getPlanetByName(request.getPlanetDeparture()));
        schedule.setPlanetArrival(planetService.getPlanetByName(request.getPlanetArrival()));
        schedule.setDepartureDatetime(request.getDepartureDatetime());
        schedule.setArrivalDatetime(request.getArrivalDatetime());

        return flightScheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        FlightSchedule schedule = getScheduleById(id);
        flightScheduleRepository.delete(schedule);
    }

    public void assignFlight(Long scheduleId, @Valid AssignFlightToScheduleDto dto) {
        var schedule = getScheduleById(scheduleId);

        var ship = shipService.getById(dto.getShipId());

        var flightStatus = flightStatusService.getFlightStatusByName(FlightStatus.PLANNED);

        var cargoStatus = cargoStatusService.getCargoStatusByName(CargoStatus.WAITING_START);

        var totalSeats = ship.getPassengerCapacity();

        var newFlight = Flight.builder()

                .name(dto.getFlightName())
                .ship(ship)
                .flightStatus(flightStatus)
                .cargoStatus(cargoStatus)
                .totalSeats(totalSeats)
                .bookedSeats(0)
                .flightSchedule(schedule)

                .build();

        flightRepository.saveAndFlush(newFlight);
    }
}
