package ru.itmo.is.course_work.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.UserDoc;

public interface UserDocRepository extends JpaRepository<UserDoc, Long> {
  List<UserDoc> findAllByUser_IdOrderById(Long userId);
}
