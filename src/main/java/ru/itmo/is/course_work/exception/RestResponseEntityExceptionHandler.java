package ru.itmo.is.course_work.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Clock;
import java.util.List;


@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @Order(1)
    protected ResponseEntity<ErrorDetails> handleCustomException(CustomException ex, WebRequest request) {
        ExceptionEnum ExceptionEnum = ex.getExceptionEnum();

        return new ResponseEntity<>(new ErrorDetails(Clock.systemDefaultZone().instant(),
                ExceptionEnum, request.getDescription(false)), ExceptionEnum.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @Order(9_000)
    protected ResponseEntity<ErrorDetails> handleAuthorizationException(Exception ex, WebRequest request) {
        log.error("AccessDeniedException: {}", ex.getMessage());

        ExceptionEnum exceptionEnum = ExceptionEnum.AUTHORIZATION_ERROR;
        return new ResponseEntity<>(new ErrorDetails(Clock.systemDefaultZone().instant(),
                exceptionEnum, request.getDescription(false)), exceptionEnum.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    @Order(10_000)
    protected ResponseEntity<ErrorDetails> handleServerException(Exception ex, WebRequest request) {
        log.error("ServerError:", ex);
        return new ResponseEntity<>(new ErrorDetails(Clock.systemDefaultZone().instant(),
                ExceptionEnum.SERVER_ERROR, request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(s -> "[%s]: %s".formatted(s.getField(), s.getDefaultMessage()))
                .toList();

        ErrorDetails errorDetails = new ErrorDetails(Clock.systemDefaultZone().instant(),
                ExceptionEnum.VALIDATION_EXCEPTION,
                "%s".formatted(StringUtils.join(errorList, ", ")));

        return handleExceptionInternal(ex, errorDetails, headers, HttpStatus.BAD_REQUEST, request);
    }
}
