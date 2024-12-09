package ru.itmo.is.course_work.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.User;
import ru.itmo.is.course_work.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public @NonNull User getByLogin(@NonNull String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new CustomException(ExceptionEnum.USER_NOT_FOUND));
    }

    public @NonNull User getById(@NonNull Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.USER_NOT_FOUND));
    }
}
