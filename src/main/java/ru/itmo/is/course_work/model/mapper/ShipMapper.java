package ru.itmo.is.course_work.model.mapper;

import java.util.List;
import org.mapstruct.*;
import ru.itmo.is.course_work.model.Ship;
import ru.itmo.is.course_work.model.dto.ShipDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ShipTypeMapper.class, ServiceClassMapper.class})
public interface ShipMapper {
    Ship toEntity(ShipDto shipDto);

    ShipDto toDto(Ship ship);

    List<ShipDto> toDto(List<Ship> ships);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ship partialUpdate(ShipDto shipDto, @MappingTarget Ship ship);
}