package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.Ship;
import ru.itmo.is.course_work.model.UserData;
import ru.itmo.is.course_work.model.dto.ShipDto;
import ru.itmo.is.course_work.service.ShipService;

import java.util.List;

@RestController
@RequestMapping("/ships")
@Validated
@Tag(
        name = "Ship controller",
        description = "Контроллер для управления кораблями и поиска доступных кораблей для пассажиров"
)
public class ShipController {

    private final ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @PostMapping("/add")
    @Operation(
            summary = "Добавить новый корабль."
    )
    public ResponseEntity<ShipDto> addNewShip(@RequestBody @Validated ShipDto shipDto) {
        ShipDto createdShip = shipService.addNewShipWithStatus(shipDto);
        return new ResponseEntity<>(createdShip, HttpStatus.CREATED);
    }

    @PostMapping("/find")
    @Operation(
            summary = "Позволяет найти доступные корабли для пассажира, основываясь на его данных."
    )
    public ResponseEntity<List<Ship>> findShipsForPassenger(@RequestBody UserData userData) {
        List<Ship> ships = shipService.findShipsForPassenger(userData);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }
}
