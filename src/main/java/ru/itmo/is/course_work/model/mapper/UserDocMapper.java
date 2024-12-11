package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.UserDoc;
import ru.itmo.is.course_work.model.dto.UserDocDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserDocTypeMapper.class})
public interface UserDocMapper {
    UserDoc toEntity(UserDocDto userDocDto);

    UserDocDto toDto(UserDoc userDoc);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserDoc partialUpdate(UserDocDto userDocDto, @MappingTarget UserDoc userDoc);
}