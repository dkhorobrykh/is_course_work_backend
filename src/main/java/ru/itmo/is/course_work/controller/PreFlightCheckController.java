package ru.itmo.is.course_work.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.service.PreFlightCheckService;

@RestController
@RequestMapping("/preflight")
@RequiredArgsConstructor
public class PreFlightCheckController {

    private final PreFlightCheckService preFlightCheckService;

    @PostMapping("/{scheduleId}/check")
    public ResponseEntity<String> performPreFlightCheck(@PathVariable Long scheduleId) {
        preFlightCheckService.performPreFlightChecks(scheduleId);
        String message = "Предполетные проверки успешно выполнены.";

        if (preFlightCheckService.isProtectionAdded()) {
            message += " Была добавлена защита от радиации и аномалий.";
            preFlightCheckService.resetProtectionAdded();
        }

        return ResponseEntity.ok(message);
    }
}
