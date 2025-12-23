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
@Table(name = "is_course_cargo_status")
public class CargoStatus {
  public static final String WAITING_START = "WAITING_START";
  public static final String CUSTOMS_CHECK = "CUSTOMS_CHECK";
  public static final String LOADING = "LOADING";
  public static final String READY = "READY";
  public static final String UNLOADING = "UNLOADING";
  public static final String COMPLETED = "COMPLETED";

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
