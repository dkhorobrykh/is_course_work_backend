package ru.itmo.is.course_work.service;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.User;
import ru.itmo.is.course_work.model.dto.RegistrationDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final UserService userService;

  private String hashPassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(hash);
    } catch (NoSuchAlgorithmException e) {
      log.error("SHA-256 algorithm not found");
      throw new CustomException(ExceptionEnum.SERVER_ERROR);
    }
  }

  public @NonNull User registerNewUser(@Valid @NonNull RegistrationDto dto) {
    var login = dto.getLogin();
    if (!userService.checkLoginUnique(login))
      throw new CustomException(ExceptionEnum.LOGIN_ALREADY_EXISTS);

    var email = dto.getEmail();
    if (!userService.checkEmailUnique(email))
      throw new CustomException(ExceptionEnum.EMAIL_ALREADY_EXISTS);

    var dateOfBirth = dto.getDateOfBirth();
    if (LocalDate.now().isBefore(dateOfBirth))
      throw new CustomException(ExceptionEnum.DATE_OF_BIRTH_IN_FUTURE);

    var hashedPassword = hashPassword("default");

    var newUser =
        User.builder()
            .login(dto.getLogin())
            .password(hashedPassword)
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .surname(dto.getSurname())
            .dateOfBirth(dateOfBirth)
            .email(email)
            .balance(10000.0)
            .keycloakId(dto.getKeycloakId())
            .build();

    return userService.save(newUser);
  }
}
