package ru.itmo.is.course_work.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    @EntityGraph(attributePaths = {"sender", "recipient", "flight", "insuranceIssueds"})
    List<Cargo> findAllBySender_IdOrderById(Long id);

    @EntityGraph(attributePaths = {"sender", "recipient", "flight", "insuranceIssueds"})
    List<Cargo> findAllByRecipient_IdOrderById(Long id);

    @EntityGraph(attributePaths = {"sender", "recipient", "flight", "insuranceIssueds"})
    List<Cargo> findAllByFlight_IdOrderById(Long id);

    @EntityGraph(attributePaths = {"sender", "recipient", "insuranceIssueds"})
    List<Cargo> findAllByFlightIsNullOrderById();

    @EntityGraph(attributePaths = {"sender", "recipient", "flight", "insuranceIssueds"})
    List<Cargo> findByShipIdOrderById(Long shipId);

    @EntityGraph(attributePaths = {
            "sender",
            "recipient",
            "flight",
            "flight.ship",
            "flight.flightSchedule",
            "insuranceIssueds",
            "insuranceIssueds.passenger"
    })
  List<Cargo> findAllByOrderById();
}
