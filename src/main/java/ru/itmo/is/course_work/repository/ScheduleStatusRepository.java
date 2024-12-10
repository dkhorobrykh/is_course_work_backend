package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.ScheduleStatus;

public interface ScheduleStatusRepository extends JpaRepository<ScheduleStatus, Long> {
}
