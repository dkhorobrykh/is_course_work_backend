package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Planet;
import ru.itmo.is.course_work.repository.PlanetRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;

    public Planet getPlanetById(Long id) {
        return planetRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.PLANET_NOT_FOUND));
    }
}
