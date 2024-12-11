package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class InsuranceIssueRequestDto {
    Long passengerId;
    Long cargoId;
    @NotNull
    Long insuranceProgramId;
    @NotNull
    Long flightId;
}
