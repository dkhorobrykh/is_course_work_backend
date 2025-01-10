package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByUserFirst_IdOrUserSecond_IdOrderById(Long userFirstId, Long userSecondId);

    Optional<Chat> findByUserFirst_IdAndUserSecond_IdOrderById(Long userFirstId, Long userSecondId);
}