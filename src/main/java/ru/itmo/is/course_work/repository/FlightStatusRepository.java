package ru.itmo.is.course_work.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.FlightStatus;

public interface FlightStatusRepository extends JpaRepository<FlightStatus, Long> {
  Optional<FlightStatus> findByNameIgnoreCase(String name);
}
