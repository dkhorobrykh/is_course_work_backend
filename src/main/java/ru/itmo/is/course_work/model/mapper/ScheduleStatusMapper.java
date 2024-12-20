package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.ScheduleStatus;
import ru.itmo.is.course_work.model.dto.ScheduleStatusDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScheduleStatusMapper {
    ScheduleStatus toEntity(ScheduleStatusDto scheduleStatusDto);

    ScheduleStatusDto toDto(ScheduleStatus scheduleStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ScheduleStatus partialUpdate(ScheduleStatusDto scheduleStatusDto, @MappingTarget ScheduleStatus scheduleStatus);
}