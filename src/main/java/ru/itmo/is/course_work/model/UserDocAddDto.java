package ru.itmo.is.course_work.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

/** DTO for {@link UserDoc} */
@Value
public class UserDocAddDto implements Serializable {
  @NotNull String userDocTypeName;

  @NotEmpty
  @Length(max = 50)
  String series;

  @NotEmpty
  @Length(max = 50)
  String number;

  @NotNull LocalDate issueDate;
  @NotNull LocalDate expirationDate;
}
