package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}