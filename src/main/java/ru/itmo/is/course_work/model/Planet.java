package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(
        name = "is_course_planet"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "galaxy_id", nullable = false)
    private Galaxy galaxy;

    @Column(name = "population", nullable = false)
    @Min(0)
    private Integer population;

    @ManyToOne(optional = false)
    @JoinColumn(name = "planet_type_id", nullable = false)
    private PlanetType planetType;

}
