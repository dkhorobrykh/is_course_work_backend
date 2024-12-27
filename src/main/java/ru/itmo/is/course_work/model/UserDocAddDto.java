package ru.itmo.is.course_work.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link UserDoc}
 */
@Value
public class UserDocAddDto implements Serializable {
    @NotNull
    String userDocTypeName;
    @NotEmpty
    @Length(max = 50)
    String series;
    @NotEmpty
    @Length(max = 50)
    String number;
    @NotNull
    LocalDate issueDate;
    @NotNull
    LocalDate expirationDate;
}