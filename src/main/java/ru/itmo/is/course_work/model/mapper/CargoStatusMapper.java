package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.CargoStatus;
import ru.itmo.is.course_work.model.dto.CargoStatusDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CargoStatusMapper {
    CargoStatus toEntity(CargoStatusDto cargoStatusDto);

    CargoStatusDto toDto(CargoStatus cargoStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CargoStatus partialUpdate(CargoStatusDto cargoStatusDto, @MappingTarget CargoStatus cargoStatus);
}