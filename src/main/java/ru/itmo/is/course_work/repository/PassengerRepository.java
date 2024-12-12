package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.Passenger;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findAllByFlight_Id(Long flightId);
}