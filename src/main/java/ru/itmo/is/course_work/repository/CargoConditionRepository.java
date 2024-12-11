package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.CargoCondition;

public interface CargoConditionRepository extends JpaRepository<CargoCondition, Long> {
}