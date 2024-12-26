package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.course_work.model.dto.AirTypeDto;
import ru.itmo.is.course_work.model.mapper.AirTypeMapper;
import ru.itmo.is.course_work.service.AirService;

@RestController
@RequestMapping("air")
@Tag(
    name = "Air type controller",
    description = "Контроллер для взаимодействия с системой типов воздуха"
)
@RequiredArgsConstructor
public class AirTypeController {
    private final AirService airService;
    private final AirTypeMapper airTypeMapper;

    @GetMapping
    @Operation(summary = "Получить список всех типов воздуха")
    public ResponseEntity<List<AirTypeDto>> getAll() {
        var result = airService.getAll();

        return ResponseEntity.ok(airTypeMapper.toDto(result));
    }
}
