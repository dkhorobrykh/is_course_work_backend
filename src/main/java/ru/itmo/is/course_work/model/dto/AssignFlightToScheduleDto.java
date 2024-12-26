package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;

@Value
public class AssignFlightToScheduleDto {
    @NotNull
    String flightName;

    @NotNull
    String shipName;
}
