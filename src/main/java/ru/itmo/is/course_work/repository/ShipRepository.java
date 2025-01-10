package ru.itmo.is.course_work.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Ship;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByServiceClasses_NameInOrderById(List<String> serviceClassNames);

    Optional<Ship> findByNameIgnoreCase(String name);

    List<Ship> findAllByOrderById();
}