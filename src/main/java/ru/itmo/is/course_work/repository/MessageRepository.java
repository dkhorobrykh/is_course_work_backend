package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}