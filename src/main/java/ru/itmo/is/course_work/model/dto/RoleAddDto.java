package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Value;

@Value
public class RoleAddDto {
  @NotNull @NotEmpty String name;

  Long flightId;

  Long planetId;

  @NotNull Boolean active;

  @NotNull Instant expirationDatetime;
}
