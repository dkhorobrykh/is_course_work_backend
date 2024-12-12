package ru.itmo.is.course_work.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightReportDto {
    private Long id;
    private String name;
    private Instant departureDatetime;
    private Instant arrivalDatetime;
    private String flightStatus;
    private String cargoStatus;
    private int totalSeats;
    private int bookedSeats;
}
