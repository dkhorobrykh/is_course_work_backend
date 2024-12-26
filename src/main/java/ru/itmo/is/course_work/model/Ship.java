package ru.itmo.is.course_work.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "is_course_ship")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    @Length(max = 100)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ship_type_id", nullable = false)
    private ShipType shipType;

    @Column(name = "number", nullable = false)
    @NotEmpty
    @Length(max = 50)
    private String number;

    @Builder.Default
    @Column(name = "registration_datetime", nullable = false)
    private Instant registrationDatetime = Instant.now();

    @Column(name = "photo")
    private String photo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "is_course_ship_service_classes",
            joinColumns = @JoinColumn(name = "ship_id"),
            inverseJoinColumns = @JoinColumn(name = "service_class_id"))
    private Set<ServiceClass> serviceClasses = new LinkedHashSet<>();

    @OneToOne(mappedBy = "ship", cascade = CascadeType.ALL, orphanRemoval = true)
    private ShipStatus shipStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "air_type_id", nullable = false)
    private AirType airType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "temperature_type_id", nullable = false)
    private TemperatureType temperatureType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "habitat_id", nullable = false)
    private Habitat habitat;

    @Column(name = "temperature", nullable = false)
    private double temperature;

    @Column(name = "atmosphere_type", nullable = false)
    private String atmosphereType;

    @Column(name = "radiation_protection_level", nullable = false)
    private double radiationProtectionLevel;

    @Column(name = "passenger_capacity", nullable = false)
    private Integer passengerCapacity;


}
