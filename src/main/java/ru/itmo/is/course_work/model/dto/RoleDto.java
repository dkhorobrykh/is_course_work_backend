package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ru.itmo.is.course_work.model.Role}
 */
@Value
public class RoleDto implements Serializable {
    Long id;
    @NotEmpty
    @Length(max = 100)
    String name;
    boolean active;
    Instant expirationDatetime;
    FlightDto flight;
    PlanetDto planet;
}