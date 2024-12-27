package ru.itmo.is.course_work.service;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
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

    public boolean checkLoginUnique(@NonNull @NotEmpty String login) {
        return userRepository.findByLogin(login).isEmpty();
    }

    public boolean checkEmailUnique(@NonNull @NotEmpty String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User addBalance(Long userId, Double amount) {
        if (amount <= 0) {
            throw new CustomException(ExceptionEnum.INVALID_AMOUNT);
        }

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.USER_NOT_FOUND));

        user.setBalance(user.getBalance() + amount);

        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
