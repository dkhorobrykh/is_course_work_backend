package ru.itmo.is.course_work.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.Role;
import ru.itmo.is.course_work.model.User;
import ru.itmo.is.course_work.util.RoleUtil;

@Service("RoleService")
@Slf4j
public class RoleService {

  public static User getCurrentUser() {
    return RoleUtil.getCurrentUser();
  }

    public static boolean isAuthenticated() {
        return RoleUtil.isAuthenticated();
    }

    public static boolean hasAdminRole() {
       return RoleUtil.hasAdminRole();
    }

    public static boolean hasAccessToFlight(Flight flight) {
      return RoleUtil.hasAccessToFlight(flight);
    }
}
