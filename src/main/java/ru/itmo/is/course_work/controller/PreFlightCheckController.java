package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.service.PreFlightCheckService;

@RestController
@RequestMapping("/preflight")
@RequiredArgsConstructor
@Tag(
        name = "PreFlight Check controller",
        description = "Контроллер для анализа и мониторинга грузопотока и пассажиропотока"
)
public class PreFlightCheckController {

    private final PreFlightCheckService preFlightCheckService;

    @Operation(
            summary = "Запускает предполетные проверки для рейса с указанным scheduleId."
    )
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
