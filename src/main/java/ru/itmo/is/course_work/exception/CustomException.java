package ru.itmo.is.course_work.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ExceptionEnum exceptionEnum;

    public CustomException(ExceptionEnum exceptionEnum) {
        super("%s: %s".formatted(exceptionEnum.getError(), exceptionEnum.getMessage()));
        this.exceptionEnum = exceptionEnum;
    }

}
