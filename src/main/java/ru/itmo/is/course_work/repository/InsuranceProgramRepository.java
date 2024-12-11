package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.InsuranceProgram;

public interface InsuranceProgramRepository extends JpaRepository<InsuranceProgram, Long> {
}