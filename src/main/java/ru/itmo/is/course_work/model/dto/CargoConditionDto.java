package ru.itmo.is.course_work.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.itmo.is.course_work.model.CargoCondition}
 */
@Value
public class CargoConditionDto implements Serializable {
    Long id;
    AirTypeDto airType;
    HabitatDto habitat;
    TemperatureTypeDto temperatureType;
}