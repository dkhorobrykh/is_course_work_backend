package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "is_course_flight_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightStatus {
  public static final String PLANNED = "PLANNED";
  public static final String APPROVED = "APPROVED";
  public static final String REGISTRATION = "REGISTRATION";
  public static final String BOARDING = "BOARDING";
  public static final String FLIGHT = "FLIGHT";
  public static final String DISEMBARKATION = "DISEMBARKATION";
  public static final String COMPLETED = "COMPLETED";

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
