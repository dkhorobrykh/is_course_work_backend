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
            LEFT JOIN f.flightStatus fstatus
            
            WHERE fs.planetDeparture.id = :departurePlanetId
                AND fs.planetArrival.id = :arrivalPlanetId
                AND (:airTypeId IS NULL OR s.airType.id = :airTypeId)
                AND (:habitatId IS NULL OR s.habitat.id = :habitatId)
                AND (:temperatureTypeId IS NULL OR s.temperatureType.id = :temperatureTypeId)
                AND fstatus.name IN :neededStatusNames
            
            ORDER BY f.departureDatetime ASC
            """)
    List<Flight> findAllAvailableForUser(Long departurePlanetId, Long arrivalPlanetId, Long airTypeId, Long habitatId, Long temperatureTypeId, List<String> neededStatusNames);

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
}
