package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Cargo;
import ru.itmo.is.course_work.model.InsuranceIssued;
import ru.itmo.is.course_work.model.dto.CargoDto;

import java.util.List;
import ru.itmo.is.course_work.model.dto.PassengerDto.InsuranceIssuedDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CargoMapper {
    @Mapping(source = "shipId", target = "ship.id")
    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "recipientId", target = "recipient.id")
    Cargo toEntity(CargoDto cargoDto);

    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(ignore = true, target = "flight.flightSchedule.flight")
    CargoDto toDto(Cargo cargo);

    List<CargoDto> toDto(List<Cargo> cargo);

    @Mapping(source = "passenger.id", target = "passengerId")
    @Mapping(source = "cargo.id", target = "cargoId")
    @Mapping(source = "insuranceProgram.name", target = "insuranceProgramName")
    InsuranceIssuedDto insuranceIssuedToInsuranceIssuedDto(InsuranceIssued insuranceIssued);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cargo partialUpdate(CargoDto cargoDto, @MappingTarget Cargo cargo);
}