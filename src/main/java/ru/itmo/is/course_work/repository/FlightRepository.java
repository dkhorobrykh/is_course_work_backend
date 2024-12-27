package ru.itmo.is.course_work.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.is.course_work.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
        SELECT f
        FROM Flight f
        LEFT JOIN Ship s on f.ship = s
        LEFT JOIN FlightSchedule fs on f.flightSchedule = fs
        LEFT JOIN f.flightStatus fstatus
        
        WHERE fs.planetDeparture.id = :departurePlanetId
            AND fs.planetArrival.id = :arrivalPlanetId
            AND (:airTypeId IS NULL OR s.airType.id = :airTypeId)
            AND (:habitatId IS NULL OR s.habitat.id = :habitatId)
            AND (:temperatureTypeId IS NULL OR s.temperatureType.id = :temperatureTypeId)
            AND fstatus.name IN :neededStatusNames
        
        ORDER BY f.departureDatetime ASC
        """)
    List<Flight> findAllAvailableForUser(
        Long departurePlanetId,
        Long arrivalPlanetId,
        Long airTypeId,
        Long habitatId,
        Long temperatureTypeId,
        List<String> neededStatusNames
    );

    @Query("""
        SELECT f
        FROM Flight f
        JOIN f.flightStatus fs
        WHERE fs.name = :statusName
        """)
    List<Flight> findByFlightStatusName(String statusName);

    @Query("""
        SELECT f
        FROM Flight f
        LEFT JOIN f.flightStatus fs
        
        WHERE fs.name IN :neededStatusNames
        """)
    List<Flight> findAllByFlightStatus_NameIn(List<String> neededStatusNames);

    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {
        "ship",
        "flightSchedule"
    })
    List<Flight> findAll();

    @Query("""
        SELECT flight
        FROM Flight flight
        WHERE flight.id = :flightId""")
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"ship", "flightSchedule"})
    Optional<Flight> findById(Long flightId);
}
