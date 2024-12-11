package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link ru.itmo.is.course_work.model.Ship}
 */
@Value
public class ShipDto implements Serializable {
    Long id;
    @NotEmpty
    @Length(max = 100)
    String name;
    Long shipTypeId;
    @NotEmpty
    @Length(max = 50)
    String number;
    Instant registrationDatetime;
    String photo;
    Set<Long> serviceClassIds;
}
