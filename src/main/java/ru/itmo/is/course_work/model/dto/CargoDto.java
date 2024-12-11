package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import ru.itmo.is.course_work.model.Cargo;

import java.io.Serializable;

/**
 * DTO for {@link Cargo}
 */
@Value
public class CargoDto implements Serializable {
    Long id;
    @NotEmpty
    @Length(max = 100)
    String name;
    Long recipientId;
    Long senderId;
    Integer weight;
    Long insuranceProgramId;
    Long shipId;
    CargoConditionDto cargoCondition;
    FlightDto flight;
}