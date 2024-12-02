package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "is_course_cargo_condition"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CargoCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "air_type_id", nullable = false)
    private AirType airType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "habitat_id", nullable = false)
    private Habitat habitat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "temperature_type_id", nullable = false)
    private TemperatureType temperatureType;

}
