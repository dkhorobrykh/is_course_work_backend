package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Planet;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
