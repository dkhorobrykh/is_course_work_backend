package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.ShipStatus;
import ru.itmo.is.course_work.model.dto.ShipStatusUpdateRequest;
import ru.itmo.is.course_work.service.ShipStatusService;

@RestController
@RequestMapping("/ship-status")
@RequiredArgsConstructor
@Tag(
        name = "Ship Status Controller",
        description = "Контроллер для получения и обновления статусов судов"
)
public class ShipStatusController {
    private final ShipStatusService shipStatusService;

    @GetMapping("/{shipId}")
    @Operation(
            summary = "Позволяет получить текущий статус корабля по его идентификатору."
    )
    public ResponseEntity<ShipStatus> getShipStatus(@PathVariable Long shipId) {
        return ResponseEntity.ok(shipStatusService.getShipStatus(shipId));
    }

    @PostMapping("/{shipId}")
    @Operation(
            summary = "Обновляет статус корабля по переданному количеству топлива."
    )
    public ResponseEntity<ShipStatus> updateShipStatus(
            @PathVariable Long shipId,
            @RequestBody ShipStatusUpdateRequest request) {
        ShipStatus updatedStatus = shipStatusService.updateShipStatus(
                shipId, request.getFuelLevel());
        return ResponseEntity.ok(updatedStatus);
    }
}
