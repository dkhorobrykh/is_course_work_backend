package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.Min;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.itmo.is.course_work.model.Planet}
 */
@Value
public class PlanetDto implements Serializable {
    Long id;
    String name;
    GalaxyDto galaxy;
    @Min(0)
    Integer population;
    PlanetTypeDto planetType;
}