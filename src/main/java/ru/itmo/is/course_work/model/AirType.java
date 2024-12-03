package ru.itmo.is.course_work.model;

import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(
        name = "is_course_air_type"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirType {
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
