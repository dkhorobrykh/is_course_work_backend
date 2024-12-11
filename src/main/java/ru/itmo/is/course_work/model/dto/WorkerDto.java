package ru.itmo.is.course_work.model.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ru.itmo.is.course_work.model.Worker}
 */
@Value
public class WorkerDto implements Serializable {
    Long id;
    UserDto user;
    RoleDto role;
    String contacts;
    String qualification;
    Instant expirationDatetime;
}