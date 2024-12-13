package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@Getter
@Setter
@Table(
        name = "is_course_revision_info"
)
@AllArgsConstructor
@NoArgsConstructor
@RevisionEntity(AuditRevisionListener.class)
public class AuditRevisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private int revision_id;

    @RevisionTimestamp
    private long revision_timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
