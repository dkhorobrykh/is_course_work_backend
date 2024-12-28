package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Objects;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(
        name = "is_course_insurance_issued"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Jacksonized
public class InsuranceIssued {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.EAGER)
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
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        InsuranceIssued worker = (InsuranceIssued) o;
        return getId() != null && Objects.equals(getId(), worker.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
