package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.UserDocType;

import java.util.Optional;

public interface UserDocTypeRepository extends JpaRepository<UserDocType, Long> {
    Optional<UserDocType> findByNameIgnoreCase(String name);
}