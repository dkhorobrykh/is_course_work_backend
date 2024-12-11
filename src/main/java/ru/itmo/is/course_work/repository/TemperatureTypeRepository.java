package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.TemperatureType;

public interface TemperatureTypeRepository extends JpaRepository<TemperatureType, Long> {
}