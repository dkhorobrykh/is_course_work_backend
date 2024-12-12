package ru.itmo.is.course_work.model.dto;

import lombok.Value;

import java.time.Instant;

@Value
public class RoleEditDto {
    Boolean active;

    Instant expirationDatetime;

    String name;
}
