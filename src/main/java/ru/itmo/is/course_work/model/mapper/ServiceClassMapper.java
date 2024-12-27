package ru.itmo.is.course_work.model.mapper;

import java.util.List;
import org.mapstruct.*;
import ru.itmo.is.course_work.model.ServiceClass;
import ru.itmo.is.course_work.model.dto.ServiceClassDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceClassMapper {
    ServiceClass toEntity(ServiceClassDto serviceClassDto);

    ServiceClassDto toDto(ServiceClass serviceClass);

    List<ServiceClassDto> toDto(List<ServiceClass> serviceClasses);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ServiceClass partialUpdate(ServiceClassDto serviceClassDto, @MappingTarget ServiceClass serviceClass);
}