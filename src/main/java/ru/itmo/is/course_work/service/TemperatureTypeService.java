package ru.itmo.is.course_work.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.TemperatureType;
import ru.itmo.is.course_work.repository.TemperatureTypeRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemperatureTypeService {
    private final TemperatureTypeRepository temperatureTypeRepository;

    public List<TemperatureType> getAll() {
        return temperatureTypeRepository.findAll();
    }

    public TemperatureType getByName(String name) {
        return temperatureTypeRepository.findByNameIgnoreCase(name)
            .orElseThrow(() -> new CustomException(ExceptionEnum.TEMPERATURE_TYPE_NOT_FOUND));
    }
}
