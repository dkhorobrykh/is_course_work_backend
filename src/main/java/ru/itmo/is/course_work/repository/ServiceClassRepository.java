package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.ServiceClass;

public interface ServiceClassRepository extends JpaRepository<ServiceClass, Long> {
}