package ru.itmo.is.course_work.model.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ru.itmo.is.course_work.model.FlightSchedule}
 */
@Value
public class FlightScheduleDto implements Serializable {
    Long id;
    FlightDto flight;
    PlanetDto planetDeparture;
    PlanetDto planetArrival;
    Instant departureDatetime;
    Instant arrivalDatetime;
    ScheduleStatusDto scheduleStatus;
}