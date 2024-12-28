package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import lombok.ToString;

@Entity
@Table(
        name = "is_course_flight_schedule"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlightSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "planet_departure_id")
    private Planet planetDeparture;

    @ManyToOne
    @JoinColumn(name = "planet_arrival_id")
    private Planet planetArrival;

    @Column(name = "departure_datetime", nullable = false)
    private Instant departureDatetime;

    @Column(name = "arrival_datetime", nullable = false)
    private Instant arrivalDatetime;

    @ManyToOne
    @JoinColumn(name = "schedule_status_id")
    private ScheduleStatus scheduleStatus;
}
