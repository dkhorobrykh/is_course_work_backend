package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link ru.itmo.is.course_work.model.ShipType}
 */
@Value
public class ShipTypeDto implements Serializable {
    Long id;
    @NotEmpty
    @Length(max = 100)
    String name;
    @NotEmpty
    @Length(max = 100)
    String outputName;
    @Min(0)
    Integer loadCapacity;
    @Min(0)
    Integer passengerCapacity;
}