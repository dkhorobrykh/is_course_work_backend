package ru.itmo.is.course_work.service;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.RefreshStorage;
import ru.itmo.is.course_work.model.User;
import ru.itmo.is.course_work.model.dto.JwtRequest;
import ru.itmo.is.course_work.model.dto.JwtResponse;
import ru.itmo.is.course_work.model.dto.RegistrationDto;
import ru.itmo.is.course_work.repository.RefreshStorageRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserService userService;
    private final RefreshStorageRepository refreshStorageRepository;
    private final JwtProvider jwtProvider;

    public JwtResponse confirm(@NotNull JwtRequest jwtRequest) {
        String login = jwtRequest.getLogin();
        String password = jwtRequest.getPassword();
        String hashedPassword = hashPassword(password);

        if (!checkCredentials(login, hashedPassword))
            throw new CustomException(ExceptionEnum.BAD_CREDENTIALS);

        return getJwtResponse(login);
    }

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

    private boolean checkCredentials(String login, String hashedPassword) {
        User user = userService.getByLogin(login);

        return hashedPassword.equals(user.getPassword());
    }

    private JwtResponse getJwtResponse(String login) {
        final User user = userService.getByLogin(login);

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        saveRefreshToken(user.getId(), refreshToken);

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse refresh(@NotNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final Long userId = Long.valueOf(claims.get("userId").toString());
            final List<String> saveRefreshToken = getRefreshToken(userId);
            if (saveRefreshToken != null && saveRefreshToken.contains(refreshToken)) {
                final User user = userService.getById(userId);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                Optional<RefreshStorage> oldToken = refreshStorageRepository.findByRefreshToken(refreshToken);
                if (oldToken.isEmpty()) {
                    log.error("Ошибка обновления существующего refresh токена в бд. Refresh токен: {}", refreshToken);
                    throw new CustomException(ExceptionEnum.SERVER_ERROR);
                }
                oldToken.get().setRefreshToken(newRefreshToken);
                refreshStorageRepository.saveAndFlush(oldToken.get());

                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new CustomException(ExceptionEnum.INVALID_TOKEN);
    }

    public List<String> getRefreshToken(Long userId) {
        final List<RefreshStorage> refreshStorage = refreshStorageRepository.getAllByUserId(userId);
        return refreshStorage.stream().map(RefreshStorage::getRefreshToken).toList();
    }

    public void saveRefreshToken(Long userId, String refreshToken) {
        RefreshStorage storage = new RefreshStorage();
        storage.setUserId(userId);
        storage.setRefreshToken(refreshToken);
        refreshStorageRepository.saveAndFlush(storage);
    }

    public void signOutFromAllDevices(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            refreshStorageRepository.deleteByUserId(Optional.ofNullable(RoleService.getCurrentUser()).orElseThrow(() -> {
                log.error("signOutFromAllDevices error");
                return new CustomException(ExceptionEnum.SERVER_ERROR);
            }).getId());
            saveRefreshToken(RoleService.getCurrentUser().getId(), refreshToken);
        } else {
            throw new CustomException(ExceptionEnum.INVALID_TOKEN);
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

        var hashedPassword = hashPassword(dto.getPassword());

        var newUser = User.builder()

                .login(dto.getLogin())
                .password(hashedPassword)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .surname(dto.getSurname())
                .dateOfBirth(dateOfBirth)
                .email(email)
                .balance(10000.0)

                .build();

        return userService.save(newUser);
    }
}
