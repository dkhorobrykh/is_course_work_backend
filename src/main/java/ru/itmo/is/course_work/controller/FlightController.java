package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class FlightController {

    private final FlightService flightService;
    private final FlightMapper flightMapper;
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

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
    public ResponseEntity<FlightDto> changeStatus(@PathVariable Long flightId, @Valid @RequestBody ChangeStatusDto dto) {
        var result = flightService.changeStatus(flightId, dto);

        return ResponseEntity.ok(flightMapper.toDto(result));
    }

    @PostMapping("{flightId}/cargo/status-change")
    @PreAuthorize("@RoleService.hasAdminRole() || @RoleService.hasAccessToFlight(@flightService.getFlightById(#flightId))")
    @Operation(summary = "Изменить статус груза на рейсе [{flightId}]")
    public ResponseEntity<FlightDto> changeCargoStatus(@PathVariable Long flightId, @Valid @RequestBody ChangeStatusDto dto) {
        var result = flightService.changeCargoStatus(flightId, dto);

        return ResponseEntity.ok(flightMapper.toDto(result));
    }
}
