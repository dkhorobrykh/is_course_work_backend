package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.AirType;
import ru.itmo.is.course_work.model.Habitat;
import ru.itmo.is.course_work.model.TemperatureType;
import ru.itmo.is.course_work.repository.AirTypeRepository;
import ru.itmo.is.course_work.repository.HabitatRepository;
import ru.itmo.is.course_work.repository.TemperatureTypeRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class NatureService {
    private final AirTypeRepository airTypeRepository;
    private final HabitatRepository habitatRepository;
    private final TemperatureTypeRepository temperatureTypeRepository;

    public AirType getAirTypeById(Long id) {
        return airTypeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.AIR_TYPE_NOT_FOUND));
    }

    public Habitat getHabitatById(Long id) {
        return habitatRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.HABITAT_NOT_FOUND));
    }

    public TemperatureType getTemperatureTypeById(Long id) {
        return temperatureTypeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.TEMPERATURE_TYPE_NOT_FOUND));
    }
}
