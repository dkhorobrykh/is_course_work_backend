package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Cargo;

import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findAllBySender_IdOrderById(Long id);
    List<Cargo> findAllByRecipient_IdOrderById(Long id);
    List<Cargo> findAllByFlight_IdOrderById(Long id);
    List<Cargo> findAllByFlightIsNullOrderById();
    List<Cargo> findByShipIdOrderById(Long shipId);

    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"insuranceIssueds", "insuranceIssueds.passenger",
        "insuranceIssueds.cargo"})
    List<Cargo> findAllByOrderById();
}