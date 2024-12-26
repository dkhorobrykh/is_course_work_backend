package ru.itmo.is.course_work.model.mapper;

import java.util.List;
import org.mapstruct.*;
import ru.itmo.is.course_work.model.AirType;
import ru.itmo.is.course_work.model.dto.AirTypeDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AirTypeMapper {
    AirType toEntity(AirTypeDto airTypeDto);

    AirTypeDto toDto(AirType airType);

    List<AirTypeDto> toDto(List<AirType> airTypes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AirType partialUpdate(AirTypeDto airTypeDto, @MappingTarget AirType airType);
}