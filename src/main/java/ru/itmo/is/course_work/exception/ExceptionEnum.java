package ru.itmo.is.course_work.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    BAD_REQUEST("Некорректный запрос", HttpStatus.BAD_REQUEST),
    AUTHORIZATION_ERROR("Ошибка авторизации", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("Переданный токен истек", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("Неверный токен", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("Пользователь не найден", HttpStatus.NOT_FOUND),
    BAD_CREDENTIALS("Неверные имя пользователя или пароль", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("Авторизация не пройдена", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Доступ к данному ресурсу запрещен", HttpStatus.FORBIDDEN),
    CHAT_NOT_FOUND("Чат не найден", HttpStatus.NOT_FOUND),
    CHAT_WITH_THE_SAME_USER("Чат с одинаковым пользователем создать нельзя", HttpStatus.BAD_REQUEST),

    SERVER_ERROR("", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String error;
    private final String message;
    private final HttpStatus httpStatus;

    ExceptionEnum(String message, HttpStatus httpStatus) {
        this.error = name();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
