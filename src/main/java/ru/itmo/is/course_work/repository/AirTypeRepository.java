package ru.itmo.is.course_work.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.AirType;

public interface AirTypeRepository extends JpaRepository<AirType, Long> {
    Optional<AirType> findByNameIgnoreCase(String name);
}