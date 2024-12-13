package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.is.course_work.model.Flight;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("""
SELECT f
FROM Flight f
LEFT JOIN Ship s on f.ship = s
LEFT JOIN FlightSchedule fs on f.flightSchedule = fs

WHERE fs.planetDeparture.id = :departurePlanetId
    AND fs.planetArrival.id = :arrivalPlanetId
    AND (:airTypeId IS NULL OR s.airType.id = :airTypeId)
    AND (:habitatId IS NULL OR s.habitat.id = :habitatId)
    AND (:temperatureTypeId IS NULL OR s.temperatureType.id = :temperatureTypeId)

ORDER BY f.departureDatetime ASC
""")
    // todo: фильтр по нужным статусам
    List<Flight> findAllAvailableForUser(Long departurePlanetId, Long arrivalPlanetId, Long airTypeId, Long habitatId, Long temperatureTypeId);

    @Query("""
    SELECT f
    FROM Flight f
    JOIN f.flightStatus fs
    WHERE fs.name = :statusName
    """)
    List<Flight> findByFlightStatusName(String statusName);
}
