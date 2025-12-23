package ru.itmo.is.course_work.util;

import jakarta.annotation.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.Role;
import ru.itmo.is.course_work.model.User;

public class RoleUtil {

  private RoleUtil() {}

  public static @Nullable User getCurrentUser() {
    return isAuthenticated()
        ? (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        : null;
  }

  public static boolean isAuthenticated() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null
        && authentication.getPrincipal() != null
        && !authentication.getPrincipal().equals("anonymousUser");
  }

    public static boolean hasAdminRole() {
        var currentUser = getCurrentUser();

        if (currentUser == null) return false;

        return currentUser.getRoles().stream().anyMatch(role -> role.getName().equals(Role.ADMIN));
    }

    public static boolean hasAccessToFlight(Flight flight) {
        var currentUser = getCurrentUser();

        if (currentUser == null) return false;

        return hasAdminRole()
                || currentUser.getRoles().stream().anyMatch(role -> role.getFlight().getId().equals(flight.getId()));
    }
}
