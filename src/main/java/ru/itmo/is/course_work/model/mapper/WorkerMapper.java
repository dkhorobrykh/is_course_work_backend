package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Worker;
import ru.itmo.is.course_work.model.dto.WorkerDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface WorkerMapper {
    Worker toEntity(WorkerDto workerDto);

    WorkerDto toDto(Worker worker);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Worker partialUpdate(WorkerDto workerDto, @MappingTarget Worker worker);
}