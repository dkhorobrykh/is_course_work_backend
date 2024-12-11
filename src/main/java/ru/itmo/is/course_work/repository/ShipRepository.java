package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Ship;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByServiceClasses_NameIn(List<String> serviceClassNames);
}