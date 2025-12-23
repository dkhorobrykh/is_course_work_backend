package ru.itmo.is.course_work.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.RefreshStorage;

public interface RefreshStorageRepository extends JpaRepository<RefreshStorage, Long> {
  List<RefreshStorage> getAllByUserId(Long id);

  void deleteByUserId(Long userId);

  Optional<RefreshStorage> findByRefreshToken(String refreshToken);
}
