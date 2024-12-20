package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link ru.itmo.is.course_work.model.Flight}
 */
@Value
public class FlightDto implements Serializable {
    Long id;
    @NotEmpty
    @Length(max = 100)
    String name;
    ShipDto ship;
    FlightStatusDto flightStatus;
    Instant departureDatetime;
    Instant arrivalDatetime;
    Set<WorkerDto> worker;
    CargoStatusDto cargoStatus;
    int totalSeats;
    int bookedSeats;
    FlightScheduleDto flightSchedule;
}