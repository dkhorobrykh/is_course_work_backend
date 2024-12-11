package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.BiologicalType;

public interface BiologicalTypeRepository extends JpaRepository<BiologicalType, Long> {
}