package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.course_work.model.dto.PassengerDto;
import ru.itmo.is.course_work.model.mapper.PassengerMapper;
import ru.itmo.is.course_work.service.PassengerService;

@RestController
@RequestMapping("passenger")
@Tag(
    name = "Passenger controller",
    description = "Контроллер для взаимодействия с системой бронирования"
)
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    @GetMapping
    @Operation(summary = "Получить список оформленных бронирований для текущего пользователя")
    public ResponseEntity<List<PassengerDto>> getAllBooksForCurrentUser() {
        var result = passengerService.getAllBooksByCurrentUser();

        return ResponseEntity.ok(passengerMapper.toDto(result));
    }
}
