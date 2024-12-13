package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.dto.FlightScheduleRequest;
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

    @GetMapping
    @Operation(
            summary = "Возвращает список всех расписаний полетов"
    )
    public List<FlightSchedule> getAllSchedules() {
        return flightScheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Возвращает расписание полета по указанному идентификатору"
    )
    public ResponseEntity<FlightSchedule> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(flightScheduleService.getScheduleById(id));
    }

    @PostMapping
    @Operation(
            summary = "Создает новое расписание полета на основе переданных данных"
    )
    public ResponseEntity<FlightSchedule> createFlightSchedule(@RequestBody @Valid FlightScheduleRequest request) {
        FlightSchedule createdSchedule = flightScheduleService.createSchedule(request);
        return ResponseEntity.ok(createdSchedule);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновляет расписание полета с указанным идентификатором на основе переданных данных"
    )
    public ResponseEntity<FlightSchedule> updateSchedule(@PathVariable Long id, @RequestBody @Valid FlightScheduleRequest request) {
        FlightSchedule updatedSchedule = flightScheduleService.updateSchedule(id, request);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаляет расписание полета с указанным идентификатором"
    )
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        flightScheduleService.deleteSchedule(id);
        return ResponseEntity.ok(null);
    }
}
