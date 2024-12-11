package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.ShipType;
import ru.itmo.is.course_work.model.dto.ShipTypeDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShipTypeMapper {
    ShipType toEntity(ShipTypeDto shipTypeDto);

    ShipTypeDto toDto(ShipType shipType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ShipType partialUpdate(ShipTypeDto shipTypeDto, @MappingTarget ShipType shipType);
}