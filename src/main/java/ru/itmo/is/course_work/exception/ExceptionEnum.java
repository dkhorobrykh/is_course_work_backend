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
    PREFLIGHT_CHECK_FAILED_FUEL("Статус топлива небезопасен.", HttpStatus.BAD_REQUEST),
    PREFLIGHT_CHECK_FAILED_ENGINE("Состояние двигателя вызывает опасения.", HttpStatus.BAD_REQUEST),
    PREFLIGHT_CHECK_FAILED_PROTECTION("Проверка перед полетом не пройдена: системы защиты не активированы.", HttpStatus.BAD_REQUEST),
    SHIP_NOT_FOUND("Корабль не найден", HttpStatus.NOT_FOUND),
    SHIP_STATUS_NOT_FOUND("Статус корабля не найден", HttpStatus.NOT_FOUND),
    SHIP_TYPE_NOT_FOUND("Указанный тип корабля не найден", HttpStatus.NOT_FOUND),
    SERVICE_CLASS_NOT_FOUND("Указанный класс обслуживания не найден", HttpStatus.NOT_FOUND),
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
    USER_DOC_TYPE_NOT_FOUND("Тип документа не найден", HttpStatus.NOT_FOUND),
    USER_DOC_NOT_FOUND("Документ не найден", HttpStatus.NOT_FOUND),
    ISSUE_DATE_IN_FUTURE("Дата выдачи не может быть позже текущей", HttpStatus.BAD_REQUEST),
    ISSUE_DATE_MUST_BE_BEFORE_EXPIRATION_DATE("Дата выдачи должна быть раньше даты истечения срока действия документа", HttpStatus.BAD_REQUEST),
    AIR_TYPE_NOT_FOUND("Тип воздуха не найден", HttpStatus.NOT_FOUND),
    HABITAT_NOT_FOUND("Тип среды обитания не найден", HttpStatus.NOT_FOUND),
    TEMPERATURE_TYPE_NOT_FOUND("Тип температурных условий не найден", HttpStatus.NOT_FOUND),

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
