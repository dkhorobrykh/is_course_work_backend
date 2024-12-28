package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.itmo.is.course_work.model.Passenger}
 */
@Value
public class PassengerDto implements Serializable {
    Long id;
    UserDocDto userDoc;
    FlightDto flight;
    ServiceClassDto serviceClass;
    UserDto user;
    List<InsuranceIssuedDto> insuranceIssueds;


    /**
     * DTO for {@link ru.itmo.is.course_work.model.InsuranceIssued}
     */
    @Value
    public static class InsuranceIssuedDto implements Serializable {

        Long id;
        Long passengerId;
        Long cargoId;
        @PositiveOrZero
        Integer totalCost;
        String insuranceProgramName;
    }
}