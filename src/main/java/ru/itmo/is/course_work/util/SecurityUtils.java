package ru.itmo.is.course_work.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class SecurityUtils {

    public Jwt getJwt() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) return null;
        return jwt;
    }

    public String getKeycloakUserId() {
        Jwt jwt = getJwt();
        if (jwt == null) throw new RuntimeException("JWT not found in context");
        return jwt.getSubject();
    }

    public Set<String> getRoles() {
        Jwt jwt = getJwt();
        if (jwt == null) return Set.of();
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) return Set.of();
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");
        return new HashSet<>(roles);
    }
}

