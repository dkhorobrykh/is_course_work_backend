package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.course_work.model.dto.ServiceClassDto;
import ru.itmo.is.course_work.model.mapper.ServiceClassMapper;
import ru.itmo.is.course_work.service.ServiceClassService;

@RestController
@RequestMapping("serviceClass")
@Tag(
    name = "Service class controller",
    description = "Контроллер для взаимодействия с системой классов обслуживания на борту"
)
@RequiredArgsConstructor
public class ServiceClassController {
    private final ServiceClassService serviceClassService;
    private final ServiceClassMapper serviceClassMapper;

    @GetMapping("{flightId}")
    @Operation(summary = "Получить список доступных классов обслуживания на рейсе [{flightId}]")
    public ResponseEntity<List<ServiceClassDto>> getServiceClassesByFlightId(@PathVariable Long flightId) {
        var result = serviceClassService.getServiceClassesByFlightId(flightId);

        return ResponseEntity.ok(serviceClassMapper.toDto(result));
    }
}
