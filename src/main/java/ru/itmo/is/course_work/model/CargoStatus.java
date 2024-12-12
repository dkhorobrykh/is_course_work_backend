package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "is_course_cargo_status"
)
public class CargoStatus {
    public final static String WAITING_START = "WAITING_START";
    public final static String CUSTOMS_CHECK = "CUSTOMS_CHECK";
    public final static String LOADING = "LOADING";
    public final static String READY = "READY";
    public final static String UNLOADING = "UNLOADING";
    public final static String COMPLETED = "COMPLETED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @NotEmpty
    @Column(name = "output_name", nullable = false)
    private String outputName;
}
