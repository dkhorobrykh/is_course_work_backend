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
            SELECT DISTINCT f
            FROM Flight f
            JOIN FETCH f.ship s
            JOIN FETCH s.airType
            JOIN FETCH s.habitat
            JOIN FETCH s.temperatureType
            JOIN FETCH f.flightSchedule fs
            JOIN FETCH fs.planetDeparture
            JOIN FETCH fs.planetArrival
            JOIN FETCH f.flightStatus fstatus
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
      List<String> neededStatusNames);

    @Query("""
            SELECT DISTINCT f
            FROM Flight f
            JOIN FETCH f.flightStatus fs
            JOIN FETCH f.ship s
            JOIN FETCH f.flightSchedule fs2
            WHERE fs.name = :statusName
            ORDER BY f.id
            """)
  List<Flight> findByFlightStatusName(String statusName);

    @Query("""
            SELECT DISTINCT f
            FROM Flight f
            JOIN FETCH f.flightStatus fs
            JOIN FETCH f.ship s
            WHERE fs.name IN :neededStatusNames
            ORDER BY f.id
            """)
    List<Flight> findAllByFlightStatus_NameIn(List<String> neededStatusNames);

  @EntityGraph(
      type = EntityGraphType.LOAD,
      attributePaths = {"ship", "flightSchedule"})
  List<Flight> findAllByOrderById();

    @EntityGraph(attributePaths = {
            "ship",
            "flightSchedule",
            "flightStatus",
            "ship.airType",
            "ship.habitat",
            "ship.temperatureType"
    })
    @Override
    Optional<Flight> findById(Long flightId);
}
