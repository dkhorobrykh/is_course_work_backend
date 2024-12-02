package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
@Tag(
        name = "Demo controller",
        description = "Тестовый контроллер"
)
public class DemoController {

    @GetMapping("{testParam}")
    @Operation(summary = "Тестовый метод")
    public ResponseEntity<List<?>> getDemo(@PathVariable @Schema(defaultValue = "testValue") String testParam) {
        return ResponseEntity.ok(List.of("string", "test", testParam));
    }
}
