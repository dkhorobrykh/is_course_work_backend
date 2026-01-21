package ru.itmo.is.course_work.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.User;
import ru.itmo.is.course_work.util.SecurityUtils;

@Slf4j
@Service
@AllArgsConstructor
public class RoleService {

    private final UserService userService;

    public User getCurrentUser() {
        if (!isAuthenticated()) return null;

        String keycloakId = SecurityUtils.getKeycloakUserId();
        return userService.getCurrentUser(keycloakId);
    }

    public static boolean isAuthenticated() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getPrincipal() instanceof Jwt;
    }

    public static boolean hasAdminRole() {
        return SecurityUtils.getRoles().contains("admin");
    }

    public boolean hasAccessToFlight(Flight flight) {
        User user = getCurrentUser();
        if (user == null) return false;
        return hasAdminRole() || user.getRoles().stream()
                .anyMatch(r -> r.getFlight().getId().equals(flight.getId()));
    }
}
