package ru.itmo.is.course_work.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.InsuranceIssued;
import ru.itmo.is.course_work.model.InsuranceProgram;
import ru.itmo.is.course_work.model.dto.InsuranceIssueRequestDto;
import ru.itmo.is.course_work.repository.InsuranceIssuedRepository;
import ru.itmo.is.course_work.repository.InsuranceProgramRepository;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceProgramRepository insuranceProgramRepository;
    private final InsuranceIssuedRepository insuranceIssuedRepository;
    private final PassengerService passengerService;
    private final CargoService cargoService;
    private final FlightService flightService;
    private final UserService userService;

    public InsuranceProgram getInsuranceProgramById(Long id) {
        return insuranceProgramRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.INSURANCE_PROGRAM_NOT_FOUND));
    }

    public InsuranceIssued issueNewInsurance(@Valid InsuranceIssueRequestDto dto) {
        var currentUser = RoleService.getCurrentUser();

        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        var passenger = dto.getPassengerId() != null
                ? passengerService.getPassengerById(dto.getPassengerId())
                : null;

        var cargo = dto.getCargoId() != null
                ? cargoService.getCargoById(dto.getCargoId())
                : null;

        if (cargo != null && passenger != null)
            throw new CustomException(ExceptionEnum.PASSENGER_AND_CARGO_SHOULD_BE_IN_DIFFERENT_INSURANCES);

        var flight = flightService.getFlightById(dto.getFlightId());

        var insuranceProgram = getInsuranceProgramById(dto.getInsuranceProgramId());

        if (!programIsNotActiveAtDatetime(insuranceProgram, flight.getDepartureDatetime()) || !programIsNotActiveAtDatetime(insuranceProgram, flight.getArrivalDatetime()))
            throw new CustomException(ExceptionEnum.WRONG_INSURANCE_PROGRAM_IS_NOT_ACTIVE_AT_FLIGHT_DATE);

        if (!insuranceProgram.isActive())
            throw new CustomException(ExceptionEnum.INSURANCE_PROGRAM_IS_NOT_ACTIVE);

        var totalCost = calculateTotalCostForInsurance(dto);
        if (currentUser.getBalance() < totalCost) {
            throw new CustomException(ExceptionEnum.INSUFFICIENT_BALANCE);
        }

        currentUser.setBalance(currentUser.getBalance() - totalCost);


        var newInsurance = InsuranceIssued.builder()

                .passenger(passenger)
                .cargo(cargo)
                .totalCost(totalCost)
                .insuranceProgram(insuranceProgram)
                .flight(flight)
                .recipient(currentUser)

                .build();

        return insuranceIssuedRepository.saveAndFlush(newInsurance);
    }

    public Integer calculateTotalCostForInsurance(@Valid InsuranceIssueRequestDto dto) {
        var insuranceProgram = getInsuranceProgramById(dto.getInsuranceProgramId());

        return (int) Math.ceil(insuranceProgram.getMinCost() * 1.5);
    }

    public static boolean programIsNotActiveAtDatetime(InsuranceProgram insuranceProgram, Instant timestamp) {
        if (insuranceProgram.getStartDatetime() != null)
            if (insuranceProgram.getEndDatetime() != null)
                return insuranceProgram.getStartDatetime().isBefore(timestamp)
                        && insuranceProgram.getEndDatetime().isAfter(timestamp);
            else
                return insuranceProgram.getStartDatetime().isBefore(timestamp);
        else
            if (insuranceProgram.getEndDatetime() != null)
                return insuranceProgram.getEndDatetime().isAfter(timestamp);
            else
                return true;
    }

    public List<InsuranceIssued> getAllIssuedInsurancesByFlightId(Long flightId) {
        return insuranceIssuedRepository.findAllByFlight_Id(flightId);
    }

    public List<InsuranceIssued> getAllIssuedInsurancesByRecipientId(Long userId) {
        return insuranceIssuedRepository.findAllByRecipient_Id(userId);
    }

    public List<InsuranceIssued> getAllIssuedInsurancesByFlightIdAndRecipientId(Long flightId, Long userId) {
        return insuranceIssuedRepository.findAllByFlight_IdAndRecipient_Id(flightId, userId);
    }

    public List<InsuranceIssued> getAllIssuedInsurances() {
        return insuranceIssuedRepository.findAll();
    }
}
