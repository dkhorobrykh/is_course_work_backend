package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
