package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class BookingFlightDto {
    @NotNull
    Long userDocId;
    @NotNull
    Long flightId;
    @NotNull
    Long serviceClassId;
}
