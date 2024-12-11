package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ru.itmo.is.course_work.model.UserDoc}
 */
@Value
public class UserDocDto implements Serializable {
    Long id;
    UserDocTypeDto userDocType;
    @NotEmpty
    @Length(max = 50)
    String series;
    @NotEmpty
    @Length(max = 50)
    String number;
    LocalDate issueDate;
    LocalDate expirationDate;
}