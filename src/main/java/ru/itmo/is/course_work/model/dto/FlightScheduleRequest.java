package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class FlightScheduleRequest {
    @NotNull
    private Long flightId;

    @NotNull
    private Long planetDepartureId;

    @NotNull
    private Long planetArrivalId;

    @NotNull
    private Instant departureDatetime;

    @NotNull
    private Instant arrivalDatetime;

    @NotNull
    private Long scheduleStatusId;
}
