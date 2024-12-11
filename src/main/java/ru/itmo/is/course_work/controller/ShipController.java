package ru.itmo.is.course_work.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.dto.ShipDto;
import ru.itmo.is.course_work.service.ShipService;

@RestController
@RequestMapping("/ships")
@Validated
public class ShipController {

    private final ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @PostMapping("/add")
    public ResponseEntity<ShipDto> addNewShip(@RequestBody @Validated ShipDto shipDto) {
        ShipDto createdShip = shipService.addNewShipWithStatus(shipDto);
        return new ResponseEntity<>(createdShip, HttpStatus.CREATED);
    }
}
