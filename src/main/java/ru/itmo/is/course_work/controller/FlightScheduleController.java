package ru.itmo.is.course_work.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.FlightSchedule;
import ru.itmo.is.course_work.model.dto.FlightScheduleRequest;
import ru.itmo.is.course_work.service.FlightScheduleService;

import java.util.List;

@RestController
@RequestMapping("/admin/schedules")
@RequiredArgsConstructor
public class FlightScheduleController {

    private final FlightScheduleService flightScheduleService;

    @GetMapping
    public List<FlightSchedule> getAllSchedules() {
        return flightScheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightSchedule> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(flightScheduleService.getScheduleById(id));
    }

    @PostMapping
    public ResponseEntity<FlightSchedule> createFlightSchedule(@RequestBody @Valid FlightScheduleRequest request) {
        FlightSchedule createdSchedule = flightScheduleService.createSchedule(request);
        return ResponseEntity.ok(createdSchedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightSchedule> updateSchedule(@PathVariable Long id, @RequestBody @Valid FlightScheduleRequest request) {
        FlightSchedule updatedSchedule = flightScheduleService.updateSchedule(id, request);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        flightScheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
