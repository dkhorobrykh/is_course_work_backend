package ru.itmo.is.course_work.model.dto;

import java.io.Serializable;
import java.time.Instant;
import lombok.Value;

/** DTO for {@link ru.itmo.is.course_work.model.FlightSchedule} */
@Value
public class FlightScheduleDto implements Serializable {
  Long id;
  PlanetDto planetDeparture;
  PlanetDto planetArrival;
  Instant departureDatetime;
  Instant arrivalDatetime;
  ScheduleStatusDto scheduleStatus;
  FlightDto flight;
}
