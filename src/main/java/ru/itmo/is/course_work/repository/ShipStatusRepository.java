package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.ShipStatus;

import java.util.Optional;

public interface ShipStatusRepository extends JpaRepository<ShipStatus, Long> {
    Optional<ShipStatus> findByShipId(Long shipId);
}
