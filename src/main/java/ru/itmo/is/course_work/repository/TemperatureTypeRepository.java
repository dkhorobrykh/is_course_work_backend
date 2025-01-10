package ru.itmo.is.course_work.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.TemperatureType;

public interface TemperatureTypeRepository extends JpaRepository<TemperatureType, Long> {
    Optional<TemperatureType> findByNameIgnoreCase(String name);

    List<TemperatureType> findAllByOrderById();
}