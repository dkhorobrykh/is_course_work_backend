package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Entity
@Table(
        name = "is_course_message"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(name = "text", nullable = false)
    @NotEmpty
    @Length(max = 100)
    private String text;

    @Builder.Default
    @Column(name = "creation_datetime", nullable = false)
    private Instant creationDatetime = Instant.now();

}
