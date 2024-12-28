package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
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
    List<InsuranceIssuedDto> insuranceIssueds;


    /**
     * DTO for {@link ru.itmo.is.course_work.model.InsuranceIssued}
     */
    @Value
    public static class InsuranceIssuedDto implements Serializable {

        Long id;
        Long passengerId;
        Long cargoId;
        Integer totalCost;
        String insuranceProgramName;
        Long flightId;
    }
}