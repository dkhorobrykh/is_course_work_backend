package ru.itmo.is.course_work.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.ShipStatus;

public interface ShipStatusRepository extends JpaRepository<ShipStatus, Long> {
  Optional<ShipStatus> findByShipId(Long shipId);
}
