package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.itmo.is.course_work.model.InsuranceIssued}
 */
@Value
public class InsuranceIssuedDto implements Serializable {
    Long id;
    PassengerDto passenger;
    CargoDto cargo;
    @PositiveOrZero
    Integer totalCost;
    InsuranceProgramDto insuranceProgram;
    UserDto recipient;
    FlightDto flight;
}