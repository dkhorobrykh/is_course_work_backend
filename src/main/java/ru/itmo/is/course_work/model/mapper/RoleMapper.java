package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.itmo.is.course_work.model.Role;
import ru.itmo.is.course_work.model.dto.RoleDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleDto toDto(Role role);
    List<RoleDto> toDto(List<Role> roles);
}
