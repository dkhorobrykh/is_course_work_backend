package ru.itmo.is.course_work.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class JwtRequest {
    private String login;
    private String password;
}
