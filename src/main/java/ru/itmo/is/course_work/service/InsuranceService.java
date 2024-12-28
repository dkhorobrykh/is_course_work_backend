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
import ru.itmo.is.course_work.model.dto.InsuranceProgramAddDto;
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

        if (!programIsNotActiveAtDatetime(insuranceProgram, flight.getFlightSchedule().getDepartureDatetime()) || !programIsNotActiveAtDatetime(insuranceProgram, flight.getFlightSchedule().getArrivalDatetime()))
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

        return insuranceProgram.getMinCost();
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

    public List<InsuranceProgram> getAllAvailableInsuranceProgramsForFlight(Long flightId) {
        var flight = flightService.getFlightById(flightId);

        return insuranceProgramRepository.findAvailableForFlight(flight.getFlightSchedule().getDepartureDatetime(),
            flight.getFlightSchedule().getArrivalDatetime());
    }

    public List<InsuranceProgram> getAllInsurancePrograms() {
        return insuranceProgramRepository.findAll();
    }

    public InsuranceProgram addNewInsuranceProgram(InsuranceProgramAddDto dto) {
        var name = dto.getName();

        var rank = dto.getRank();
        if (rank < 0)
            throw new CustomException(ExceptionEnum.VALIDATION_EXCEPTION);

        var minCost = dto.getMinCost();
        if (minCost <= 0)
            throw new CustomException(ExceptionEnum.VALIDATION_EXCEPTION);

        var refundAmount = dto.getRefundAmount();
        if (refundAmount <= 0)
            throw new CustomException(ExceptionEnum.VALIDATION_EXCEPTION);

        var startDatetime = dto.getStartDatetime();

        var endDatetime = dto.getEndDatetime();

        if (startDatetime.isAfter(endDatetime))
            throw new CustomException(ExceptionEnum.VALIDATION_EXCEPTION);

        var newProgram = InsuranceProgram.builder()

            .name(name)
            .rank(rank)
            .minCost(minCost)
            .refundAmount(refundAmount)
            .active(true)
            .startDatetime(startDatetime)
            .endDatetime(endDatetime)

            .build();

        return insuranceProgramRepository.saveAndFlush(newProgram);

    }
}
