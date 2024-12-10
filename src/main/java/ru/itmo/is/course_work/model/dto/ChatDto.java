package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import ru.itmo.is.course_work.model.Chat;
import ru.itmo.is.course_work.model.Message;
import ru.itmo.is.course_work.model.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link Chat}
 */
@Value
public class ChatDto implements Serializable {
    Long id;
    UserDto userFirst;
    UserDto userSecond;
    Instant creationDatetime;
    List<MessageDto> messages;

    /**
     * DTO for {@link User}
     */
    @Value
    public static class UserDto implements Serializable {
        Long id;
        @NotEmpty
        @Length(max = 100)
        String firstName;
        @NotEmpty
        @Length(max = 100)
        String lastName;
        @NotEmpty
        @Length(max = 100)
        String surname;
    }

    /**
     * DTO for {@link Message}
     */
    @Value
    public static class MessageDto implements Serializable {
        Long id;
        @NotEmpty
        @Length(max = 100)
        String text;
        Instant creationDatetime;
    }
}