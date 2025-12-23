package ru.itmo.is.course_work.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.is.course_work.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

  @Query(
      """
         SELECT DISTINCT p
         FROM Passenger p
         JOIN FETCH p.flight f
         JOIN FETCH p.user u
         WHERE p.flight.id = :flightId
         ORDER BY p.id
         """)
  List<Passenger> findAllByFlight_Id(Long flightId);

  @EntityGraph(attributePaths = {
            "flight",
            "user",
            "flight.flightSchedule",
            "flight.ship"
  })
  @Override
  Optional<Passenger> findById(Long id);

    @Query("""
            SELECT DISTINCT p
            FROM Passenger p
            JOIN FETCH p.flight f
            JOIN FETCH f.flightSchedule fs
            JOIN FETCH f.ship s
            WHERE p.user.id = :userId
            ORDER BY p.id
            """)
    List<Passenger> findAllByUser_Id(Long userId);
}
