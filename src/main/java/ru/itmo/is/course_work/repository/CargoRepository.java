package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Cargo;

import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findAllBySender_Id(Long id);
    List<Cargo> findAllByRecipient_Id(Long id);
    List<Cargo> findAllByFlight_Id(Long id);
    List<Cargo> findAllByFlightIsNull();
    List<Cargo> findByShipId(Long shipId);

    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"insuranceIssueds", "insuranceIssueds.passenger",
        "insuranceIssueds.cargo"})
    List<Cargo> findAll();
}