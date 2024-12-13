package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.dto.UserDto;
import ru.itmo.is.course_work.model.mapper.UserMapper;
import ru.itmo.is.course_work.service.UserService;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Управление пользователями")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/{userId}/balance")
    @Operation(summary = "Пополнить баланс пользователя")
    public ResponseEntity<UserDto> addBalance(@PathVariable Long userId, @RequestParam Double amount) {
        var updatedUser = userService.addBalance(userId, amount);

        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }
}
