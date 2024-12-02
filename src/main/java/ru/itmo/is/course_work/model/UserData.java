package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "is_course_user_data"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "biological_type_id", nullable = false)
    private BiologicalType biologicalType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "physiological_type_id", nullable = false)
    private PhysiologicalType physiologicalType;

    @Column(name = "contact", nullable = false)
    private String contact;

}
