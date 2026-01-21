package ru.itmo.is.course_work.service;

import io.micrometer.core.instrument.Counter;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
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
  private final Counter flightBookingCounter;
  private final Counter bookingErrorCounter;
  private final RoleService roleService;

  public List<Passenger> getAllBooksByCurrentUser() {
    var currentUser = roleService.getCurrentUser();
    if (currentUser == null) throw new CustomException(ExceptionEnum.UNAUTHORIZED);

    return passengerRepository.findAllByUser_Id(currentUser.getId());
  }

  public Passenger getPassengerById(Long id) {
    return passengerRepository
        .findById(id)
        .orElseThrow(() -> new CustomException(ExceptionEnum.PASSENGER_NOT_FOUND));
  }

  @Transactional
  public Passenger bookFlight(@Valid BookingFlightDto dto) {
      try {
          var currentUser = roleService.getCurrentUser();
          if (currentUser == null) throw new CustomException(ExceptionEnum.UNAUTHORIZED);

          var userDoc = userDocService.getUserDocById(dto.getUserDocId());
          var flight = flightService.getFlightById(dto.getFlightId());
          var serviceClass = serviceClassService.getServiceClassById(dto.getServiceClassId());

          double flightCost = serviceClass.getCost();

          ZoneId zoneId = ZoneId.systemDefault();
          LocalDate departureDate =
                  flight.getFlightSchedule().getDepartureDatetime().atZone(zoneId).toLocalDate();

          userDocService.validateDocumentForFlight(dto.getUserDocId(), dto.getFlightId());

          if (currentUser.getBalance() < flightCost) {
              throw new CustomException(ExceptionEnum.INSUFFICIENT_BALANCE);
          }

          if (flight.getBookedSeats() >= flight.getTotalSeats())
              throw new CustomException(ExceptionEnum.NO_FREE_SEATS);

          flight.setBookedSeats(flight.getBookedSeats() + 1);

          var newPassenger =
                  Passenger.builder()
                          .userDoc(userDoc)
                          .flight(flight)
                          .serviceClass(serviceClass)
                          .user(currentUser)
                          .build();

          currentUser.setBalance(currentUser.getBalance() - flightCost);
          userRepository.save(currentUser);
          flightBookingCounter.increment();
          return passengerRepository.saveAndFlush(newPassenger);
      } catch (Exception e) {
          bookingErrorCounter.increment();
          throw new RuntimeException(e);
      }
  }
}
