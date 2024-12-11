package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Passenger;
import ru.itmo.is.course_work.model.dto.PassengerDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserDocMapper.class, FlightMapper.class, ServiceClassMapper.class, UserMapper.class})
public interface PassengerMapper {
    Passenger toEntity(PassengerDto passengerDto);

    PassengerDto toDto(Passenger passenger);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Passenger partialUpdate(PassengerDto passengerDto, @MappingTarget Passenger passenger);
}