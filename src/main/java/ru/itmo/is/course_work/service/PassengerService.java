package ru.itmo.is.course_work.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Passenger;
import ru.itmo.is.course_work.model.dto.BookingFlightDto;
import ru.itmo.is.course_work.repository.PassengerRepository;
import ru.itmo.is.course_work.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final UserDocService userDocService;
    private final FlightService flightService;
    private final ServiceClassService serviceClassService;
    private final UserRepository userRepository;

    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PASSENGER_NOT_FOUND));
    }

    @Transactional
    public Passenger bookFlight(@Valid BookingFlightDto dto) {
        var currentUser = RoleService.getCurrentUser();
        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        var userDoc = userDocService.getUserDocById(dto.getUserDocId());
        var flight = flightService.getFlightById(dto.getFlightId());
        var serviceClass = serviceClassService.getServiceClassById(dto.getServiceClassId());

        double flightCost = serviceClass.getCost();

        if (currentUser.getBalance() < flightCost) {
            throw new CustomException(ExceptionEnum.INSUFFICIENT_BALANCE);
        }

        if (flight.getBookedSeats() >= flight.getTotalSeats())
            throw new CustomException(ExceptionEnum.NO_FREE_SEATS);

        flight.setBookedSeats(flight.getBookedSeats() + 1);

        var newPassenger = Passenger.builder()
                .userDoc(userDoc)
                .flight(flight)
                .serviceClass(serviceClass)
                .user(currentUser)
                .build();

        currentUser.setBalance(currentUser.getBalance() - flightCost);
        userRepository.save(currentUser);
        return passengerRepository.saveAndFlush(newPassenger);
    }
}
