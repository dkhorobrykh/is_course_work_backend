package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.PlanetType;
import ru.itmo.is.course_work.model.dto.PlanetTypeDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TemperatureTypeMapper.class, HabitatMapper.class, AirTypeMapper.class})
public interface PlanetTypeMapper {
    PlanetType toEntity(PlanetTypeDto planetTypeDto);

    PlanetTypeDto toDto(PlanetType planetType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PlanetType partialUpdate(PlanetTypeDto planetTypeDto, @MappingTarget PlanetType planetType);
}