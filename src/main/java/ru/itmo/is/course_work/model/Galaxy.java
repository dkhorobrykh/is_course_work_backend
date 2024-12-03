package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(
        name = "is_course_galaxy"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Galaxy {
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
