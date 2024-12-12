package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ChangeStatusDto {
    @NotNull
    @NotEmpty
    String newStatus;
}
