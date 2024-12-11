package ru.itmo.is.course_work.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.ShipStatus;
import ru.itmo.is.course_work.service.ShipStatusService;

@RestController
@RequestMapping("/ship-status")
@RequiredArgsConstructor
public class ShipStatusController {
    private final ShipStatusService shipStatusService;

    @GetMapping("/{shipId}")
    public ResponseEntity<ShipStatus> getShipStatus(@PathVariable Long shipId) {
        return ResponseEntity.ok(shipStatusService.getShipStatus(shipId));
    }

    @PostMapping("/{shipId}")
    public ResponseEntity<ShipStatus> updateShipStatus(
            @PathVariable Long shipId,
            @RequestBody ShipStatusUpdateRequest request) {
        ShipStatus updatedStatus = shipStatusService.updateShipStatus(
                shipId, request.fuelLevel());
        return ResponseEntity.ok(updatedStatus);
    }
}

record ShipStatusUpdateRequest(Double fuelLevel) {
}
