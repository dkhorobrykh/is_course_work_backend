package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
        name = "is_course_user"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", nullable = false)
    @Length(min = 6, max = 100)
    @NotEmpty
    private String login;

    @Column(name = "password", nullable = false)
    @NotEmpty
    private String password;

    @Column(name = "first_name", nullable = false)
    @NotEmpty
    @Length(max = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotEmpty
    @Length(max = 100)
    private String lastName;

    @Column(name = "surname", nullable = false)
    @NotEmpty
    @Length(max = 100)
    private String surname;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "email", nullable = false)
    @NotEmpty
    private String email;

    @ManyToMany
    @JoinTable(name = "is_course_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new LinkedHashSet<>();

}
