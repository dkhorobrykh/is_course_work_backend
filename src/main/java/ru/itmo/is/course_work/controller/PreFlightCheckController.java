package ru.itmo.is.course_work.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.UserData;
import ru.itmo.is.course_work.service.PreFlightCheckService;

@RestController
@RequestMapping("/preflight")
@RequiredArgsConstructor
public class PreFlightCheckController {

    private final PreFlightCheckService preFlightCheckService;

    @PostMapping("/{scheduleId}/check")
    public ResponseEntity<String> performPreFlightCheck(@PathVariable Long scheduleId) {
        preFlightCheckService.performPreFlightChecks(scheduleId);
        return ResponseEntity.ok("Предполетные проверки успешно выполнены.");
    }
}
