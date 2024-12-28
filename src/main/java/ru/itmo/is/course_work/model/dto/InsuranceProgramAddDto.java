package ru.itmo.is.course_work.model.dto;

import java.io.Serializable;
import java.time.Instant;
import lombok.Value;

/**
 * DTO for {@link ru.itmo.is.course_work.model.InsuranceProgram}
 */
@Value
public class InsuranceProgramAddDto implements Serializable {

    String name;
    Integer rank;
    Integer minCost;
    Integer refundAmount;
    boolean active;
    Instant startDatetime;
    Instant endDatetime;
}