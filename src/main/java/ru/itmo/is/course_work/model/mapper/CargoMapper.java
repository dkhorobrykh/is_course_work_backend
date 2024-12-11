package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Cargo;
import ru.itmo.is.course_work.model.dto.CargoDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CargoMapper {
    @Mapping(source = "shipId", target = "ship.id")
    @Mapping(source = "insuranceProgramId", target = "insuranceProgram.id")
    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "recipientId", target = "recipient.id")
    Cargo toEntity(CargoDto cargoDto);

    @InheritInverseConfiguration(name = "toEntity")
    CargoDto toDto(Cargo cargo);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cargo partialUpdate(CargoDto cargoDto, @MappingTarget Cargo cargo);
}