package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

/** DTO for {@link ru.itmo.is.course_work.model.Message} */
@Value
public class NewMessageDto implements Serializable {
  @NotEmpty
  @Length(max = 100)
  String text;
}
