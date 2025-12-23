package ru.itmo.is.course_work.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import lombok.Value;

/** DTO for {@link Cargo} */
@Value
public class CargoAddDto implements Serializable {
  @NotNull Long recipientId;
  @NotNull Long senderId;
  @NotNull @Positive Integer weight;
  @NotNull CargoConditionDto cargoCondition;

  /** DTO for {@link CargoCondition} */
  @Value
  public static class CargoConditionDto implements Serializable {
    @NotNull Long airTypeId;
    @NotNull Long habitatId;
    @NotNull Long temperatureTypeId;
  }
}
