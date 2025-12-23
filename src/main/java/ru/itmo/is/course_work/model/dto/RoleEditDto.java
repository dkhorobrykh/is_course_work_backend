package ru.itmo.is.course_work.model.dto;

import java.time.Instant;
import lombok.Value;

@Value
public class RoleEditDto {
  Boolean active;

  Instant expirationDatetime;

  String name;
}
