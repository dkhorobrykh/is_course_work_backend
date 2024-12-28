package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.dto.FlightScheduleDto;

import java.util.List;
import ru.itmo.is.course_work.service.FlightScheduleService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses
    = {FlightMapper.class, PlanetMapper.class, PlanetMapper.class, ScheduleStatusMapper.class}, imports =
    {FlightScheduleService.class, FlightMapper.class})
public abstract class FlightScheduleMapper {
    @Autowired
    protected FlightScheduleService flightScheduleService;

    @Autowired
    protected FlightMapper flightMapper;

    public abstract FlightSchedule toEntity(FlightScheduleDto flightScheduleDto);

    @Mapping(expression = "java(flightMapper.toDto(flightScheduleService.getFlightByScheduleId(flightSchedule.getId()" +
        ")))", target = "flight")
//    @Mapping(ignore = true, target = "flight.flightSchedule")
    public abstract FlightScheduleDto toDto(FlightSchedule flightSchedule);
    public abstract List<FlightScheduleDto> toDto(List<FlightSchedule> flightSchedules);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract FlightSchedule partialUpdate(FlightScheduleDto flightScheduleDto,
        @MappingTarget FlightSchedule flightSchedule);
}