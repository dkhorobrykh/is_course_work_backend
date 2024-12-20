package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.itmo.is.course_work.model.CargoStatus}
 */
@Value
public class CargoStatusDto implements Serializable {
    Long id;
    @NotNull
    @NotEmpty
    String name;
    @NotNull
    @NotEmpty
    String outputName;
}