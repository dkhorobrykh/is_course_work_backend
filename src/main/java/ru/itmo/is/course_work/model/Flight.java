package ru.itmo.is.course_work.model;

import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "is_course_flight"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Jacksonized
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    @Length(max = 100)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ship_id", nullable = false)
    private Ship ship;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_status_id", nullable = false)
    private FlightStatus flightStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cargo_status_id", nullable = false)
    private CargoStatus cargoStatus;

    @Column(name = "departure_datetime")
    private Instant departureDatetime;

    @Column(name = "arrival_datetime")
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

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "flight_schedule_id")
    private FlightSchedule flightSchedule;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Flight flight = (Flight) o;
        return getId() != null && Objects.equals(getId(), flight.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
