package ru.itmo.is.course_work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.is.course_work.model.InsuranceIssued;

import java.util.List;

public interface InsuranceIssuedRepository extends JpaRepository<InsuranceIssued, Long> {
    List<InsuranceIssued> findAllByFlight_Id(Long flightId);
    List<InsuranceIssued> findAllByRecipient_Id(Long recipientId);
    List<InsuranceIssued> findAllByFlight_IdAndRecipient_Id(Long flightId, Long recipientId);
}