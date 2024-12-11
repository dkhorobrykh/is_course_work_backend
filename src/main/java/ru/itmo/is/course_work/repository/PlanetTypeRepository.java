package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.PlanetType;

public interface PlanetTypeRepository extends JpaRepository<PlanetType, Long> {
}