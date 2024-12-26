package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.course_work.model.dto.HabitatDto;
import ru.itmo.is.course_work.model.mapper.HabitatMapper;
import ru.itmo.is.course_work.service.HabitatService;

@RestController
@RequestMapping("habitat")
@Tag(
    name = "Habitat controller",
    description = "Контроллер для взаимодействия с системой среды обитания"
)
@RequiredArgsConstructor
public class HabitatController {

    private final HabitatService habitatService;
    private final HabitatMapper habitatMapper;

    @GetMapping
    @Operation(summary = "Получить список всех сред обитаний")
    public ResponseEntity<List<HabitatDto>> getAllHabitats() {
        var result = habitatService.getAllHabitats();

        return ResponseEntity.ok(habitatMapper.toDto(result));
    }
}
