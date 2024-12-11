package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Cargo;
import ru.itmo.is.course_work.repository.CargoRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;

    public Cargo getCargoById(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.CARGO_NOT_FOUND));
    }
}
