package ru.itmo.is.course_work.model.mapper;

import java.util.List;
import org.mapstruct.*;
import ru.itmo.is.course_work.model.Habitat;
import ru.itmo.is.course_work.model.dto.HabitatDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface HabitatMapper {
    Habitat toEntity(HabitatDto habitatDto);

    HabitatDto toDto(Habitat habitat);

    List<HabitatDto> toDto(List<Habitat> habitats);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Habitat partialUpdate(HabitatDto habitatDto, @MappingTarget Habitat habitat);
}