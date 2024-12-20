package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.dto.FlightScheduleDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {FlightMapper.class, PlanetMapper.class, PlanetMapper.class, ScheduleStatusMapper.class})
public interface FlightScheduleMapper {
    FlightSchedule toEntity(FlightScheduleDto flightScheduleDto);

    @AfterMapping
    default void linkFlight(@MappingTarget FlightSchedule flightSchedule) {
        Flight flight = flightSchedule.getFlight();
        if (flight != null) {
            flight.setFlightSchedule(flightSchedule);
        }
    }

    FlightScheduleDto toDto(FlightSchedule flightSchedule);
    List<FlightScheduleDto> toDto(List<FlightSchedule> flightSchedules);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FlightSchedule partialUpdate(FlightScheduleDto flightScheduleDto, @MappingTarget FlightSchedule flightSchedule);
}