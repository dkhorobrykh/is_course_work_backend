package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.UserDoc;

import java.util.List;

public interface UserDocRepository extends JpaRepository<UserDoc, Long> {
    List<UserDoc> findAllByUser_IdOrderById(Long userId);
}