package ru.itmo.is.course_work.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itmo.is.course_work.model.ServiceClass;

public interface ServiceClassRepository extends JpaRepository<ServiceClass, Long> {

    @Query("""
        SELECT sc
        FROM Flight f
        JOIN f.ship s
        JOIN s.serviceClasses sc
        WHERE f.id = :flightId
        ORDER BY sc.id
        """)
    List<ServiceClass> findAllByFlight_Id(@Param("flightId") Long flightId);
}
