package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import ru.itmo.is.course_work.model.User;

/** DTO for {@link User} */
@Value
public class UserDto implements Serializable {
  Long id;

  @NotEmpty
  @Length(min = 6, max = 100)
  String login;

  @NotEmpty
  @Length(max = 100)
  String firstName;

  @NotEmpty
  @Length(max = 100)
  String lastName;

  @NotEmpty
  @Length(max = 100)
  String surname;

  LocalDate dateOfBirth;
  @NotEmpty String email;
  Set<RoleDto> roles;
  Double balance;
}
