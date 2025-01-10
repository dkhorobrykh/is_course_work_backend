package ru.itmo.is.course_work.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import ru.itmo.is.course_work.model.Role;

public interface RoleRepository extends RevisionRepository<Role, Long, Long>, JpaRepository<Role, Long> {
    List<Role> findAllByOrderById();
}