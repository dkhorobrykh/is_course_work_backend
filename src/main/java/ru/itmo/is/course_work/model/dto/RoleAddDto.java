package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.Instant;

@Value
public class RoleAddDto {
    @NotNull
    @NotEmpty
    String name;

    Long flightId;

    Long planetId;

    @NotNull
    Boolean active;

    @NotNull
    Instant expirationDatetime;
}
