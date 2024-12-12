package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(
        name = "is_course_flight_status"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightStatus {
    public final static String PLANNED = "PLANNED";
    public final static String APPROVED = "APPROVED";
    public final static String REGISTRATION = "REGISTRATION";
    public final static String BOARDING = "BOARDING";
    public final static String FLIGHT = "FLIGHT";
    public final static String DISEMBARKATION = "DISEMBARKATION";
    public final static String COMPLETED = "COMPLETED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    @Length(max = 100)
    private String name;

    @Column(name = "output_name", nullable = false)
    @NotEmpty
    @Length(max = 100)
    private String outputName;
}
