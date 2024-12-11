package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.UserDocType;
import ru.itmo.is.course_work.model.dto.UserDocTypeDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDocTypeMapper {
    UserDocType toEntity(UserDocTypeDto userDocTypeDto);

    UserDocTypeDto toDto(UserDocType userDocType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserDocType partialUpdate(UserDocTypeDto userDocTypeDto, @MappingTarget UserDocType userDocType);
}