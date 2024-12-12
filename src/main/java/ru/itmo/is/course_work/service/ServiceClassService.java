package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.ServiceClass;
import ru.itmo.is.course_work.repository.ServiceClassRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceClassService {

    private final ServiceClassRepository serviceClassRepository;

    public ServiceClass getServiceClassById(Long id) {
        return serviceClassRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.SERVICE_CLASS_NOT_FOUND));
    }
}
