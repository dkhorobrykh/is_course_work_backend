package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Data;

@Data
public class FlightScheduleRequest {
  @NotNull private String planetDeparture;

  @NotNull private String planetArrival;

  @NotNull private Instant departureDatetime;

  @NotNull private Instant arrivalDatetime;
}
