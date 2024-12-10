package ru.itmo.is.course_work.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ErrorDetails {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, without = JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String details;

    public ErrorDetails(Instant timestamp, ExceptionEnum constant, String details) {
        setTimestamp(timestamp);
        setError(constant.getError());
        setMessage(constant.getMessage());
        setDetails(details);
        setStatus(constant.getHttpStatus().value());
    }

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new CustomException(ExceptionEnum.SERVER_ERROR);
        }
    }
}
