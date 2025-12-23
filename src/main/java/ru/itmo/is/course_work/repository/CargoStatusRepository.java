package ru.itmo.is.course_work.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.CargoStatus;

public interface CargoStatusRepository extends JpaRepository<CargoStatus, Long> {
  Optional<CargoStatus> findByNameIgnoreCase(String name);
}
