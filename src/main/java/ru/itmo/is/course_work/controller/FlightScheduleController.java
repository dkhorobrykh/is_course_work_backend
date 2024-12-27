package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.dto.AssignFlightToScheduleDto;
import ru.itmo.is.course_work.model.dto.FlightScheduleDto;
import ru.itmo.is.course_work.model.dto.FlightScheduleRequest;
import ru.itmo.is.course_work.model.mapper.FlightScheduleMapper;
import ru.itmo.is.course_work.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/admin/schedules")
@RequiredArgsConstructor
@Tag(
        name = "Flight schedule controller",
        description = "Контроллер для взаимодействия с расписанием"
)
public class FlightScheduleController {

    private final FlightScheduleService flightScheduleService;
    private final FlightScheduleMapper flightScheduleMapper;

    @GetMapping
    @Operation(
            summary = "Возвращает список всех расписаний полетов"
    )
    public ResponseEntity<List<FlightScheduleDto>> getAllSchedules() {
        var result = flightScheduleService.getAllSchedules();

        return ResponseEntity.ok(flightScheduleMapper.toDto(result));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Возвращает расписание полета по указанному идентификатору"
    )
    public ResponseEntity<FlightScheduleDto> getScheduleById(@PathVariable Long id) {
        var result = flightScheduleService.getScheduleById(id);

        return ResponseEntity.ok(flightScheduleMapper.toDto(result));
    }

    @PostMapping
    @Operation(
            summary = "Создает новое расписание полета"
    )
    public ResponseEntity<FlightScheduleDto> createFlightSchedule(@RequestBody @Valid FlightScheduleRequest request) {
        var createdSchedule = flightScheduleService.createSchedule(request);
        return ResponseEntity.ok(flightScheduleMapper.toDto(createdSchedule));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновляет расписание полета с указанным идентификатором на основе переданных данных"
    )
    public ResponseEntity<FlightScheduleDto> updateSchedule(@PathVariable Long id, @RequestBody @Valid FlightScheduleRequest request) {
        var updatedSchedule = flightScheduleService.updateSchedule(id, request);
        return ResponseEntity.ok(flightScheduleMapper.toDto(updatedSchedule));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаляет расписание полета с указанным идентификатором"
    )
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        flightScheduleService.deleteSchedule(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("{scheduleId}/assignFlight")
    @Operation(summary = "Назначить рейс на расписание")
    public ResponseEntity<List<FlightScheduleDto>> assignFlight(@PathVariable Long scheduleId, @RequestBody @Valid AssignFlightToScheduleDto dto) {
        flightScheduleService.assignFlight(scheduleId, dto);
        var result = flightScheduleService.getAllSchedules();

        for (var s : result) {
            System.out.println(s.getFlight());
        }

        return ResponseEntity.ok(flightScheduleMapper.toDto(result));
    }
}
