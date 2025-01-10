package ru.itmo.is.course_work.repository;

import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.is.course_work.model.InsuranceProgram;

public interface InsuranceProgramRepository extends JpaRepository<InsuranceProgram, Long> {
    @Query("""
        SELECT ip
        FROM InsuranceProgram ip
        WHERE ip.active = TRUE
          AND ip.startDatetime <= :departureDatetime
          AND ip.endDatetime >= :arrivalDatetime
        ORDER BY ip.id""")
    List<InsuranceProgram> findAvailableForFlight(Instant departureDatetime, Instant arrivalDatetime);

    List<InsuranceProgram> findAllByOrderById();
}