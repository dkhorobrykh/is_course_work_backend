package ru.itmo.is.course_work.model;

import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
        name = "is_course_flight"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    @Length(max = 100)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ship_id", nullable = false)
    private Ship ship;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_status_id", nullable = false)
    private FlightStatus flightStatus;

    @Column(name = "departure_datetime", nullable = false)
    private Instant departureDatetime;

    @Column(name = "arrival_datetime", nullable = false)
    private Instant arrivalDatetime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "is_course_flight_workers",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private Set<Worker> worker = new LinkedHashSet<>();

    @Column(name = "total_seats", nullable = false)
    private int totalSeats;

    @Column(name = "booked_seats", nullable = false)
    private int bookedSeats;

}
