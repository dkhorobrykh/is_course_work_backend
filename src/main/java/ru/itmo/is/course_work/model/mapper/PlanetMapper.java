package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Planet;
import ru.itmo.is.course_work.model.dto.PlanetDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {GalaxyMapper.class, PlanetTypeMapper.class})
public interface PlanetMapper {
    Planet toEntity(PlanetDto planetDto);

    PlanetDto toDto(Planet planet);
    List<PlanetDto> toDto(List<Planet> planetList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Planet partialUpdate(PlanetDto planetDto, @MappingTarget Planet planet);
}