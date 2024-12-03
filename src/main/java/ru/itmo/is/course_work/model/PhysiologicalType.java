package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(
        name = "is_course_physiological_type"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhysiologicalType {
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "temperature_type_id", nullable = false)
    private TemperatureType temperatureType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "habitat_id", nullable = false)
    private Habitat habitat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "air_type_id", nullable = false)
    private AirType airType;

}
