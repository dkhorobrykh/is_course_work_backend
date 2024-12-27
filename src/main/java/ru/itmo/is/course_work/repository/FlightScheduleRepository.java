package ru.itmo.is.course_work.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.is.course_work.model.FlightSchedule;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {
    @Query("""
        SELECT fs
        FROM FlightSchedule fs
        WHERE fs.id = :id""")
    Optional<FlightSchedule> findByIdEquals(Long id);

    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"flight", "planetDeparture", "planetArrival", "flight" +
        ".ship"})
    List<FlightSchedule> findAll();
}
