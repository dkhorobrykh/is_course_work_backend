package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class FlightScheduleRequest {
    @NotNull
    private String planetDeparture;

    @NotNull
    private String planetArrival;

    @NotNull
    private Instant departureDatetime;

    @NotNull
    private Instant arrivalDatetime;
}
