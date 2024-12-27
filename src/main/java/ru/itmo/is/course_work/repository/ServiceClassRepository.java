package ru.itmo.is.course_work.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.is.course_work.model.ServiceClass;

public interface ServiceClassRepository extends JpaRepository<ServiceClass, Long> {
    @Query("""
        SELECT sc
        FROM Flight flight
        LEFT JOIN flight.ship ship
        LEFT JOIN ship.serviceClasses sc
        WHERE flight.id = :flightId
        """)
    List<ServiceClass> findAllByFlight_Id(Long flightId);
}