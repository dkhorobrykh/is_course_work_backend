package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(
        name = "is_course_passenger"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_doc_id", nullable = false)
    private UserDoc userDoc;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_class_id", nullable = false)
    private ServiceClass serviceClass;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "passenger", orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id desc")
    private List<InsuranceIssued> insuranceIssueds = new ArrayList<>();

}
