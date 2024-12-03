package ru.itmo.is.course_work.service;

import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.model.RefreshStorage;
import ru.itmo.is.course_work.model.User;
import ru.itmo.is.course_work.model.dto.JwtRequest;
import ru.itmo.is.course_work.model.dto.JwtResponse;
import ru.itmo.is.course_work.repository.RefreshStorageRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        log.info(hashedPassword);

        if (!checkCredentials(login, hashedPassword))
            throw new RuntimeException("Bad credentials");

        return getJwtResponse(login);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
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
                    throw new RuntimeException("Ошибка обновления существующего refresh токена в бд");
                }
                oldToken.get().setRefreshToken(newRefreshToken);
                refreshStorageRepository.saveAndFlush(oldToken.get());

                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new RuntimeException("Invalid token");
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
            refreshStorageRepository.deleteByUserId(Optional.ofNullable(RoleService.getCurrentUser()).orElseThrow(() -> new RuntimeException("signOutFromAllDevices error")).getId());
            saveRefreshToken(RoleService.getCurrentUser().getId(), refreshToken);
        } else {
            throw new RuntimeException("Invalid token");
        }
    }
}
