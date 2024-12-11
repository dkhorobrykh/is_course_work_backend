package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.InsuranceProgram;
import ru.itmo.is.course_work.model.dto.InsuranceProgramDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InsuranceProgramMapper {
    InsuranceProgram toEntity(InsuranceProgramDto insuranceProgramDto);

    InsuranceProgramDto toDto(InsuranceProgram insuranceProgram);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InsuranceProgram partialUpdate(InsuranceProgramDto insuranceProgramDto, @MappingTarget InsuranceProgram insuranceProgram);
}