package ru.itmo.is.course_work.model.dto;

import java.io.Serializable;
import lombok.Value;

/** DTO for {@link ru.itmo.is.course_work.model.CargoCondition} */
@Value
public class CargoConditionDto implements Serializable {
  Long id;
  AirTypeDto airType;
  HabitatDto habitat;
  TemperatureTypeDto temperatureType;
}
