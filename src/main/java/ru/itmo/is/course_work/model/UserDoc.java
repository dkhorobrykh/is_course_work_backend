package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Entity
@Table(
        name = "is_course_user_doc"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_doc_type_id", nullable = false)
    private UserDocType userDocType;

    @Column(name = "series", nullable = false)
    @NotEmpty
    @Length(max = 50)
    private String series;

    @Column(name = "number", nullable = false)
    @NotEmpty
    @Length(max = 50)
    private String number;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
