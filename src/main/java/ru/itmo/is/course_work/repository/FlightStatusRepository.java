package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.FlightStatus;

import java.util.Optional;

public interface FlightStatusRepository extends JpaRepository<FlightStatus, Long> {
    Optional<FlightStatus> findByNameIgnoreCase(String name);
}