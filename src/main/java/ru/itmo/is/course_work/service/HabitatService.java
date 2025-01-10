package ru.itmo.is.course_work.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Habitat;
import ru.itmo.is.course_work.repository.HabitatRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class HabitatService {
    private final HabitatRepository habitatRepository;

    public List<Habitat> getAllHabitats() {
        return habitatRepository.findAllByOrderById();
    }

    public Habitat getByName(String name) {
        return habitatRepository.findByNameIgnoreCase(name)
            .orElseThrow(() -> new CustomException(ExceptionEnum.HABITAT_NOT_FOUND));
    }
}
