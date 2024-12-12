package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.CargoStatus;

import java.util.Optional;

public interface CargoStatusRepository extends JpaRepository<CargoStatus, Long> {
    Optional<CargoStatus> findByNameIgnoreCase(String name);
}