package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.TemperatureType;
import ru.itmo.is.course_work.model.dto.TemperatureTypeDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TemperatureTypeMapper {
    TemperatureType toEntity(TemperatureTypeDto temperatureTypeDto);

    TemperatureTypeDto toDto(TemperatureType temperatureType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TemperatureType partialUpdate(TemperatureTypeDto temperatureTypeDto, @MappingTarget TemperatureType temperatureType);
}