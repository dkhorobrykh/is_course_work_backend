package ru.itmo.is.course_work.model.mapper;

import java.util.List;
import org.mapstruct.*;
import ru.itmo.is.course_work.model.InsuranceIssued;
import ru.itmo.is.course_work.model.Passenger;
import ru.itmo.is.course_work.model.dto.PassengerDto;
import ru.itmo.is.course_work.model.dto.PassengerDto.InsuranceIssuedDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserDocMapper.class, FlightMapper.class, ServiceClassMapper.class, UserMapper.class})
public interface PassengerMapper {
    Passenger toEntity(PassengerDto passengerDto);

    @Mapping(ignore = true, target = "flight.flightSchedule.flight")
    PassengerDto toDto(Passenger passenger);

    List<PassengerDto> toDto(List<Passenger> passengers);

    @Mapping(source = "passenger.id", target = "passengerId")
    @Mapping(source = "cargo.id", target = "cargoId")
    @Mapping(source = "insuranceProgram.name", target = "insuranceProgramName")
    InsuranceIssuedDto insuranceIssuedToInsuranceIssuedDto(InsuranceIssued insuranceIssued);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Passenger partialUpdate(PassengerDto passengerDto, @MappingTarget Passenger passenger);
}