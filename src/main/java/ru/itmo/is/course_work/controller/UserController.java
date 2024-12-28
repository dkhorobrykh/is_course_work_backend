package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.dto.UserDto;
import ru.itmo.is.course_work.model.mapper.UserMapper;
import ru.itmo.is.course_work.service.RoleService;
import ru.itmo.is.course_work.service.UserService;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Управление пользователями")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("all")
    @PreAuthorize("@RoleService.hasAdminRole()")
    @Operation(summary = "Получить список всех пользователей")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var users = userService.getAll();

        return ResponseEntity.ok(userMapper.toDto(users));
    }

    @PostMapping("/{userId}/balance")
    @Operation(summary = "Пополнить баланс пользователя")
    public ResponseEntity<UserDto> addBalance(@PathVariable Long userId, @RequestParam Double amount) {
        var updatedUser = userService.addBalance(userId, amount);

        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получить пользователя по id")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        var user = userService.getById(userId);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping
    @Operation(summary = "Получить текущего пользователя")
    public ResponseEntity<UserDto> getCurrentUser() {
        var currentUser = RoleService.getCurrentUser();
        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        var user = userService.getById(currentUser.getId());

        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
