package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link ru.itmo.is.course_work.model.AirType}
 */
@Value
public class AirTypeDto implements Serializable {
    Long id;
    @NotEmpty
    @Length(max = 100)
    String name;
    @NotEmpty
    @Length(max = 100)
    String outputName;
}