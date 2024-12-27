package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.dto.BookingFlightDto;
import ru.itmo.is.course_work.model.dto.ChangeStatusDto;
import ru.itmo.is.course_work.model.dto.FlightDto;
import ru.itmo.is.course_work.model.dto.PassengerDto;
import ru.itmo.is.course_work.model.mapper.FlightMapper;
import ru.itmo.is.course_work.model.mapper.PassengerMapper;
import ru.itmo.is.course_work.service.FlightService;
import ru.itmo.is.course_work.service.PassengerService;

import java.util.List;

@RestController
@RequestMapping("flight")
@RequiredArgsConstructor
@Tag(
        name = "Flight controller",
        description = "Контроллер взаимодействия с системой бронирования рейсов"
)
@Slf4j
public class FlightController {

    private final FlightService flightService;
    private final FlightMapper flightMapper;
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    @GetMapping
    @Operation(summary = "Получить список всех рейсов")
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        var result = flightService.getAllFlights();

        return ResponseEntity.ok(flightMapper.toDto(result));
    }

    @GetMapping("{flightId}")
    @Operation(summary = "Получить информацию о рейсе [{flightId}]")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Long flightId) {
        var result = flightService.getFlightById(flightId);

        return ResponseEntity.ok(flightMapper.toDto(result));
    }

    @GetMapping("select")
    @Operation(summary = "Подобрать подходящие рейсы в зависимости от характеристик текущего пользователя")
    public ResponseEntity<List<FlightDto>> selectAvailableFlightsForCurrentUser(@RequestParam Long departurePlanetId, @RequestParam Long arrivalPlanetId) {
        var result = flightService.selectAvailableFlights(departurePlanetId, arrivalPlanetId);

        return ResponseEntity.ok(flightMapper.toDto(result));
    }

    @PostMapping("book")
    @Operation(summary = "Забронировать рейс для текущего пользователя")
    public ResponseEntity<PassengerDto> bookFlight(@Valid @RequestBody BookingFlightDto dto) {
        var result = passengerService.bookFlight(dto);

        return ResponseEntity.ok(passengerMapper.toDto(result));
    }

    @PostMapping("{flightId}/status-change")
    @PreAuthorize("@RoleService.hasAdminRole() || @RoleService.hasAccessToFlight(@flightService.getFlightById(#flightId))")
    @Operation(summary = "Изменить статус рейса [{flightId}]")
    public ResponseEntity<FlightDto> changeStatus(@PathVariable Long flightId) {
        var result = flightService.changeStatus(flightId);

        return ResponseEntity.ok(flightMapper.toDto(result));
    }

    @PostMapping("{flightId}/cargo/status-change")
    @PreAuthorize("@RoleService.hasAdminRole() || @RoleService.hasAccessToFlight(@flightService.getFlightById(#flightId))")
    @Operation(summary = "Изменить статус груза на рейсе [{flightId}]")
    public ResponseEntity<FlightDto> changeCargoStatus(@PathVariable Long flightId) {
        var result = flightService.changeCargoStatus(flightId);

        return ResponseEntity.ok(flightMapper.toDto(result));
    }
}
