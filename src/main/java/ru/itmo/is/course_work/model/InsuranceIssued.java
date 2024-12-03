package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "is_course_insurance_issued"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InsuranceIssued {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @Column(name = "total_cost", nullable = false)
    @PositiveOrZero
    private Integer totalCost;

    @ManyToOne(optional = false)
    @JoinColumn(name = "insurance_program_id", nullable = false)
    private InsuranceProgram insuranceProgram;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

}
