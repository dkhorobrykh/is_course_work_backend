package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "is_course_chat"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_first_id", nullable = false)
    private User userFirst;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_second_id", nullable = false)
    private User userSecond;

    @Builder.Default
    @Column(name = "creation_datetime", nullable = false)
    private Instant creationDatetime = Instant.now();

    @OneToMany(mappedBy = "chat", orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("creationDatetime")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

}
