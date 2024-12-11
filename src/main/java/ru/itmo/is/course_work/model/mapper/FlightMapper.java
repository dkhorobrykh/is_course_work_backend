package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.dto.FlightDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ShipMapper.class, FlightStatusMapper.class, WorkerMapper.class})
public interface FlightMapper {
    Flight toEntity(FlightDto flightDto);

    FlightDto toDto(Flight flight);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Flight partialUpdate(FlightDto flightDto, @MappingTarget Flight flight);
}