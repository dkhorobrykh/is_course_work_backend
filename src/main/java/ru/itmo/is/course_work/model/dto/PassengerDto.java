package ru.itmo.is.course_work.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.itmo.is.course_work.model.Passenger}
 */
@Value
public class PassengerDto implements Serializable {
    Long id;
    UserDocDto userDoc;
    FlightDto flight;
    ServiceClassDto serviceClass;
    UserDto user;
}