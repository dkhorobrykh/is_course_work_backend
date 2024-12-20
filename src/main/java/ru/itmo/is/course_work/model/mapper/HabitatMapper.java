package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Habitat;
import ru.itmo.is.course_work.model.dto.HabitatDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface HabitatMapper {
    Habitat toEntity(HabitatDto habitatDto);

    HabitatDto toDto(Habitat habitat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Habitat partialUpdate(HabitatDto habitatDto, @MappingTarget Habitat habitat);
}