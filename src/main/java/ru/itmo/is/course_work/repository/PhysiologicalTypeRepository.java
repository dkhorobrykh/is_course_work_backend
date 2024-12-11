package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.PhysiologicalType;

public interface PhysiologicalTypeRepository extends JpaRepository<PhysiologicalType, Long> {
}