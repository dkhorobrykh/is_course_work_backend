package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
        name = "is_course_flight_schedule"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "flight_id")
    private Flight flight;

//    @OneToOne(orphanRemoval = true)
//    @JoinColumn(name = "flight_id")
//    private Flight flight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "planet_departure_id", nullable = false)
    private Planet planetDeparture;

    @ManyToOne(optional = false)
    @JoinColumn(name = "planet_arrival_id", nullable = false)
    private Planet planetArrival;

    @Column(name = "departure_datetime", nullable = false)
    private Instant departureDatetime;

    @Column(name = "arrival_datetime", nullable = false)
    private Instant arrivalDatetime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "schedule_status_id", nullable = false)
    private ScheduleStatus scheduleStatus;
}
