package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.CargoStatus;
import ru.itmo.is.course_work.repository.CargoStatusRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CargoStatusService {
    private final CargoStatusRepository cargoStatusRepository;

    public CargoStatus getCargoStatusByName(String name) {
        return cargoStatusRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new CustomException(ExceptionEnum.CARGO_STATUS_NOT_FOUND));
    }
}
