package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.FlightSchedule;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {
}
