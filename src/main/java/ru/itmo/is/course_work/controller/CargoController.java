package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.CargoAddDto;
import ru.itmo.is.course_work.model.dto.CargoDto;
import ru.itmo.is.course_work.model.mapper.CargoMapper;
import ru.itmo.is.course_work.service.CargoService;

import java.util.List;

@RestController
@RequestMapping("cargo")
@RequiredArgsConstructor
@Tag(
        name = "Cargo controller",
        description = "Контроллер для взаимодействия с системой грузов"
)
public class CargoController {

    private final CargoService cargoService;
    private final CargoMapper cargoMapper;

    @PostMapping
    @Operation(summary = "Добавить груз текущему пользователю")
    public ResponseEntity<List<CargoDto>> addCargo(@Valid @RequestBody CargoAddDto dto) {
        cargoService.addCargo(dto);
        var result = cargoService.getAllWhereCurrentUserIsSender();

        return ResponseEntity.ok(cargoMapper.toDto(result));
    }

    @GetMapping("sender")
    @Operation(summary = "Получить список грузов, в которых текущий пользователь числится отправителем")
    public ResponseEntity<List<CargoDto>> getAllWhereCurrentUserIsSender() {
        var result = cargoService.getAllWhereCurrentUserIsSender();

        return ResponseEntity.ok(cargoMapper.toDto(result));
    }

    @GetMapping("recipient")
    @Operation(summary = "Получить список грузов, в которых текущий пользователь числится получаетелем")
    public ResponseEntity<List<CargoDto>> getAllWhereCurrentUserIsRecipient() {
        var result = cargoService.getAllWhereCurrentUserIsRecipient();

        return ResponseEntity.ok(cargoMapper.toDto(result));
    }

    @GetMapping("{flightId}")
    @PreAuthorize("@RoleService.hasAdminRole() || @RoleService.hasAccessToFlight(#flightId)")
    @Operation(summary = "Получить список грузов, относящихся к рейсу [{flightId}]")
    public ResponseEntity<List<CargoDto>> getAllByFlightId(@PathVariable Long flightId) {
        var result = cargoService.getAllByFlightId(flightId);

        return ResponseEntity.ok(cargoMapper.toDto(result));
    }

    @GetMapping
    @PreAuthorize("@RoleService.hasAdminRole()")
    @Operation(summary = "Получить все грузы, зарегистрированные в системе")
    public ResponseEntity<List<CargoDto>> getAllCargo() {
        var result = cargoService.getAllCargo();

        return ResponseEntity.ok(cargoMapper.toDto(result));
    }
}
