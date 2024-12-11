package ru.itmo.is.course_work.service;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.model.Role;
import ru.itmo.is.course_work.model.User;

@Service("RoleService")
@Slf4j
public class RoleService {

    public static boolean userIdEqualsCurrent(Long userId) {
        User user = getCurrentUser();
        return userId != null && user != null && userId.equals(user.getId());
    }

    public static @Nullable User getCurrentUser() {
        return isAuthenticated()
                ? (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                : null;
    }

    public static boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getPrincipal() != null && !authentication.getPrincipal().equals("anonymousUser");
    }

    public static boolean hasAdminRole() {
        var currentUser = getCurrentUser();

        if (currentUser == null) return false;

        return currentUser.getRoles().stream().anyMatch(role -> role.getName().equals(Role.ADMIN));
    }

    public static boolean hasAccessToFlight(Long flightId) {
        // TODO
        return hasAdminRole();
    }
}
