package ru.itmo.is.course_work.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "is_course_ship_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShipStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "ship_id", nullable = false)
    private Ship ship;

    @Column(name = "fuel_level", nullable = false)
    private Double fuelLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_status", nullable = false)
    private FuelStatus fuelStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine_status", nullable = false)
    private EngineStatus engineStatus;

    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    public enum FuelStatus {
        FULL,
        HIGH,
        MEDIUM,
        LOW,
        CRITICAL
    }

    public enum EngineStatus {
        OK,
        WARNING,
        CRITICAL
    }
}