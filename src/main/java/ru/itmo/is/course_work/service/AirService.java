package ru.itmo.is.course_work.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.AirType;
import ru.itmo.is.course_work.repository.AirTypeRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirService {
    private final AirTypeRepository airTypeRepository;

    public List<AirType> getAll() {
        return airTypeRepository.findAllByOrderById();
    }

    public AirType getByName(String name) {
        return airTypeRepository.findByNameIgnoreCaseOrderById(name)
            .orElseThrow(() -> new CustomException(ExceptionEnum.AIR_TYPE_NOT_FOUND));
    }
}
