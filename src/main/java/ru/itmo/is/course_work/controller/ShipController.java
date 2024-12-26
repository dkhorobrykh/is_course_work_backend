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
import ru.itmo.is.course_work.model.dto.ShipAddDto;
import ru.itmo.is.course_work.model.dto.ShipDto;
import ru.itmo.is.course_work.model.mapper.ShipMapper;
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
    private final ShipMapper shipMapper;

    @Autowired
    public ShipController(ShipService shipService, ShipMapper shipMapper) {
        this.shipService = shipService;
        this.shipMapper = shipMapper;
    }

    @PostMapping("/add")
    @Operation(
            summary = "Добавить новый корабль."
    )
    public ResponseEntity<ShipDto> addNewShip(@RequestBody @Validated ShipAddDto shipDto) {
        Ship createdShip = shipService.addNewShipWithStatus(shipDto);
        return ResponseEntity.ok(shipMapper.toDto(createdShip));
    }

    @PostMapping("/find")
    @Operation(
            summary = "Позволяет найти доступные корабли для пассажира, основываясь на его данных."
    )
    public ResponseEntity<List<Ship>> findShipsForPassenger(@RequestBody UserData userData) {
        List<Ship> ships = shipService.findShipsForPassenger(userData);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @PatchMapping("/{id}/conditions")
    @Operation(
            summary = "Обновление условий на борту корабля."
    )
    public ResponseEntity<String> updateShipConditions(
            @PathVariable Long id,
            @RequestParam double temperature,
            @RequestParam String atmosphereType,
            @RequestParam double radiationProtectionLevel) {

        boolean updated = shipService.updateShipConditions(id, temperature, atmosphereType, radiationProtectionLevel);

        if (updated) {
            return ResponseEntity.ok("Условия на борту корабля успешно обновлены.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Не удалось обновить условия на борту корабля. Проверьте соответствие условий для груза и пассажиров.");
        }
    }

    @GetMapping
    @Operation(summary = "Получить список всех кораблей")
    public ResponseEntity<List<ShipDto>> getAllShips() {
        var result = shipService.getAll();

        return ResponseEntity.ok(shipMapper.toDto(result));
    }

    @PutMapping("{shipId}")
    @Operation(summary = "Обновить корабль по [{shipId}]")
    public ResponseEntity<ShipDto> updateShip(@PathVariable Long shipId, @RequestBody ShipDto shipDto) {
        var result = shipService.update(shipId, shipDto);

        return ResponseEntity.ok(shipMapper.toDto(result));
    }
}
