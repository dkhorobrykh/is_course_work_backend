package ru.itmo.is.course_work.model.dto;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

/**
 * DTO for {@link ru.itmo.is.course_work.model.Ship}
 */
@Value
public class ShipAddDto implements Serializable {

    @NotEmpty
    @Length(max = 100)
    String name;
    @NotEmpty
    @Length(max = 50)
    String number;
    Instant registrationDatetime;
    String photo;
    Set<ServiceClassDto1> serviceClasses;
    AirTypeDto airType;
    TemperatureTypeDto temperatureType;
    HabitatDto habitat;
    Integer passengerCapacity;


    /**
     * DTO for {@link ru.itmo.is.course_work.model.ServiceClass}
     */
    @Value
    public static class ServiceClassDto1 implements Serializable {

        @NotEmpty
        @Length(max = 100)
        String name;
        double cost;
    }


    /**
     * DTO for {@link ru.itmo.is.course_work.model.AirType}
     */
    @Value
    public static class AirTypeDto implements Serializable {

        @NotEmpty
        @Length(max = 100)
        String name;
    }


    /**
     * DTO for {@link ru.itmo.is.course_work.model.TemperatureType}
     */
    @Value
    public static class TemperatureTypeDto implements Serializable {

        @NotEmpty
        @Length(max = 100)
        String name;
    }


    /**
     * DTO for {@link ru.itmo.is.course_work.model.Habitat}
     */
    @Value
    public static class HabitatDto implements Serializable {

        @NotEmpty
        @Length(max = 100)
        String name;
    }
}