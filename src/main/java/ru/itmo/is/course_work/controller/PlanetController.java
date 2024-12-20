package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.course_work.model.dto.PlanetDto;
import ru.itmo.is.course_work.model.mapper.PlanetMapper;
import ru.itmo.is.course_work.service.PlanetService;

import java.util.List;

@RestController
@RequestMapping("planet")
@Tag(
        name = "Planet controller",
        description = "Контроллер управления списком планет"
)
@RequiredArgsConstructor
public class PlanetController {
    private final PlanetService planetService;
    private final PlanetMapper planetMapper;

    @GetMapping("all")
    @Operation(summary = "Получить список всех доступных планет")
    public ResponseEntity<List<PlanetDto>> getAllPlanets() {
        var result = planetService.getAll();

        return ResponseEntity.ok(planetMapper.toDto(result));
    }
}
