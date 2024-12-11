package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Value
public class RegistrationDto {
    @NotNull
    @NotEmpty
    @Length(min = 4, max = 100)
    String login;
    @NotNull
    @NotEmpty
    @Length(min = 4)
    String password;
    @NotNull
    @NotEmpty
    @Length(max = 100)
    String firstName;
    @NotNull
    @NotEmpty
    @Length(max = 100)
    String lastName;
    @NotNull
    @NotEmpty
    @Length(max = 100)
    String surname;
    @NotNull
    LocalDate dateOfBirth;
    @NotNull
    @Email
    @NotEmpty
    String email;
}
