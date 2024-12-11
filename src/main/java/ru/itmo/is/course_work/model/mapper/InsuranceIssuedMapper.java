package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.InsuranceIssued;
import ru.itmo.is.course_work.model.dto.InsuranceIssuedDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {PassengerMapper.class, CargoMapper.class, InsuranceProgramMapper.class, UserMapper.class, FlightMapper.class})
public interface InsuranceIssuedMapper {
    InsuranceIssued toEntity(InsuranceIssuedDto insuranceIssuedDto);

    InsuranceIssuedDto toDto(InsuranceIssued insuranceIssued);
    List<InsuranceIssuedDto> toDto(List<InsuranceIssued> insuranceIssueds);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InsuranceIssued partialUpdate(InsuranceIssuedDto insuranceIssuedDto, @MappingTarget InsuranceIssued insuranceIssued);
}