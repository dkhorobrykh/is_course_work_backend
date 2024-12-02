package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "is_course_insurance_program"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InsuranceProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rank", nullable = false)
    private Integer rank;

    @Column(name = "min_cost", nullable = false)
    private Integer minCost;

    @Column(name = "refund_amount", nullable = false)
    private Integer refundAmount;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private boolean active = false;

    @Column(name = "start_datetime", nullable = false)
    private Instant startDatetime;

    @Column(name = "end_datetime", nullable = false)
    private Instant endDatetime;
}
