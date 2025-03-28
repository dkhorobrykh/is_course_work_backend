package ru.itmo.is.course_work.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.is.course_work.model.Passenger;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query("""
SELECT distinct p
FROM Passenger p
WHERE p.flight.id = :flightId
ORDER BY p.id""")
    List<Passenger> findAllByFlight_Id(Long flightId);

    @Query("""
        SELECT p
        FROM Passenger p
        WHERE p.id = :id""")
    Optional<Passenger> findById(Long id);

    @Query("""
        SELECT distinct p
        FROM Passenger p
        WHERE p.user.id = :userId
        ORDER BY p.id""")
    List<Passenger> findAllByUser_Id(Long userId);
}