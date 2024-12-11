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
    PASSENGER_AND_CARGO_SHOULD_BE_IN_DIFFERENT_INSURANCES("Пассажир и груз должны быть оформлены разными страховками", HttpStatus.BAD_REQUEST),
    INSURANCE_PROGRAM_NOT_FOUND("Страховая программа не найдена", HttpStatus.NOT_FOUND),
    PASSENGER_NOT_FOUND("Пассажир не найден", HttpStatus.NOT_FOUND),
    CARGO_NOT_FOUND("Груз не найден", HttpStatus.NOT_FOUND),
    FLIGHT_NOT_FOUND("Рейс не найден", HttpStatus.NOT_FOUND),
    WRONG_INSURANCE_PROGRAM_IS_NOT_ACTIVE_AT_FLIGHT_DATE("Программа страхования недоступна в момент перелета", HttpStatus.BAD_REQUEST),
    INSURANCE_PROGRAM_IS_NOT_ACTIVE("Программа страхования не активна", HttpStatus.BAD_REQUEST),
    LOGIN_ALREADY_EXISTS("Пользователь с таким логином уже существует", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS("Пользователь с таким email уже существует", HttpStatus.CONFLICT),
    DATE_OF_BIRTH_IN_FUTURE("Переданная дата рождения находится в будущем", HttpStatus.BAD_REQUEST),
    VALIDATION_EXCEPTION("Ошибка валидации данных", HttpStatus.BAD_REQUEST),

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
