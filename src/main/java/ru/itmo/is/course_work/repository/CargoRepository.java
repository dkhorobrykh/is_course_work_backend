package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Cargo;

import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findAllBySender_Id(Long id);
    List<Cargo> findAllByRecipient_Id(Long id);
    List<Cargo> findAllByFlight_Id(Long id);
    List<Cargo> findAllByFlightIsNull();
    List<Cargo> findByShipId(Long shipId);
}