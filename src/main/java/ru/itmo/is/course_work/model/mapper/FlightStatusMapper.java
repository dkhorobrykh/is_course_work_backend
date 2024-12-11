package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.FlightStatus;
import ru.itmo.is.course_work.model.dto.FlightStatusDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FlightStatusMapper {
    FlightStatus toEntity(FlightStatusDto flightStatusDto);

    FlightStatusDto toDto(FlightStatus flightStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FlightStatus partialUpdate(FlightStatusDto flightStatusDto, @MappingTarget FlightStatus flightStatus);
}