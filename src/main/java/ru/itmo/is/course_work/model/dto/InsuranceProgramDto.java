package ru.itmo.is.course_work.model.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ru.itmo.is.course_work.model.InsuranceProgram}
 */
@Value
public class InsuranceProgramDto implements Serializable {
    Long id;
    Integer rank;
    Integer minCost;
    Integer refundAmount;
    boolean active;
    Instant startDatetime;
    Instant endDatetime;
}