package ru.itmo.is.course_work.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Planet;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
  Optional<Planet> findByNameIgnoreCase(String name);

  List<Planet> findAllByOrderById();
}
