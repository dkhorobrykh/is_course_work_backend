package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.dto.*;
import ru.itmo.is.course_work.model.mapper.UserMapper;
import ru.itmo.is.course_work.service.AuthService;
import ru.itmo.is.course_work.service.UserService;

@RestController
@RequestMapping("authorization")
@Tag(name = "Auth controller", description = "Авторизация пользователей")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("confirm")
    @Operation(summary = "Проверить credentials, получить токены при успешной проверке")
    public ResponseEntity<JwtResponse> confirm(@Valid @RequestBody JwtRequest jwtRequest) {
        final JwtResponse token = authService.confirm(jwtRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    @Operation(summary = "Обновить токены")
    public ResponseEntity<JwtResponse> getNewTokens(@Valid @RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("signOut")
    @Operation(summary = "Выйти со всех устройств")
    public ResponseEntity<?> signOutFromAllDevices(@Valid @RequestBody RefreshJwtRequest request) {
        authService.signOutFromAllDevices(request.getRefreshToken());

        return ResponseEntity.ok(null);
    }

    @PostMapping("register")
    @Operation(summary = "Зарегистрировать нового пользователя")
    public ResponseEntity<UserDto> registerNewUser(@Valid @RequestBody RegistrationDto dto) {
        var result = authService.registerNewUser(dto);

        return ResponseEntity.ok(userMapper.toDto(result));
    }
}
