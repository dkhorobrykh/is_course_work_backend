package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Galaxy;
import ru.itmo.is.course_work.model.dto.GalaxyDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GalaxyMapper {
    Galaxy toEntity(GalaxyDto galaxyDto);

    GalaxyDto toDto(Galaxy galaxy);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Galaxy partialUpdate(GalaxyDto galaxyDto, @MappingTarget Galaxy galaxy);
}