package ru.itmo.is.course_work.model.mapper;

import java.time.Instant;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.ScheduleStatus;
import ru.itmo.is.course_work.model.dto.FlightDto;

import java.util.List;
import ru.itmo.is.course_work.model.dto.FlightScheduleDto;
import ru.itmo.is.course_work.model.dto.PlanetDto;
import ru.itmo.is.course_work.model.dto.ScheduleStatusDto;
import ru.itmo.is.course_work.service.FlightScheduleService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses
    = {ShipMapper.class, FlightStatusMapper.class, WorkerMapper.class, PlanetMapper.class, GalaxyMapper.class})
public abstract class FlightMapper {
    @Autowired
    public FlightScheduleService flightScheduleService;
    @Autowired
    public PlanetMapper planetMapper;

    public abstract Flight toEntity(FlightDto flightDto);

//    @Mapping(expression = "java(flightScheduleService.getByFlightId(flight.getId()))", target =
//        "flightSchedule")
    @Mapping(expression = "java(planetMapper.toDto(flightScheduleService.getByFlightId(flightSchedule.getFlight()" +
        ".getId()).getPlanetDeparture()))", target = "flightSchedule.planetDeparture")
    @Mapping(expression = "java(planetMapper.toDto(flightScheduleService.getByFlightId(flightSchedule.getFlight()" +
        ".getId()).getPlanetArrival()))", target = "flightSchedule.planetArrival")
    @Mapping(ignore = true, target = "flightSchedule.flight")
    public abstract FlightDto toDto(Flight flight);

    public abstract List<FlightDto> toDto(List<Flight> flights);

//    protected FlightScheduleDto flightScheduleToFlightScheduleDto(FlightSchedule flightSchedule) {
//        if ( flightSchedule == null ) {
//            return null;
//        }
//
//        Long id = null;
//        Instant departureDatetime = null;
//        Instant arrivalDatetime = null;
//        ScheduleStatusDto scheduleStatus = null;
//
//        id = flightSchedule.getId();
//        departureDatetime = flightSchedule.getDepartureDatetime();
//        arrivalDatetime = flightSchedule.getArrivalDatetime();
//        scheduleStatus = scheduleStatusToScheduleStatusDto( flightSchedule.getScheduleStatus() );
//
//        PlanetDto planetDeparture = planetMapper.toDto(flightScheduleService.getByFlightId(flightSchedule.getFlight().getId()).getPlanetDeparture());
//        PlanetDto planetArrival = planetMapper.toDto(flightScheduleService.getByFlightId(flightSchedule.getFlight().getId()).getPlanetArrival());
//        FlightDto flight = null;
//
//        FlightScheduleDto flightScheduleDto = new FlightScheduleDto( id, planetDeparture, planetArrival, departureDatetime, arrivalDatetime, scheduleStatus, flight );
//
//        return flightScheduleDto;
//    }
//
//    public abstract ScheduleStatusDto scheduleStatusToScheduleStatusDto(ScheduleStatus scheduleStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Flight partialUpdate(FlightDto flightDto, @MappingTarget Flight flight);
}