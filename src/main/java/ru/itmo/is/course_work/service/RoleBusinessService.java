package ru.itmo.is.course_work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Role;
import ru.itmo.is.course_work.repository.RoleRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleBusinessService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role createRole(Role role) {
        validateAndSetRoleStatus(role);
        Role savedRole = roleRepository.save(role);
        log.debug("Created role id={}, active={}", savedRole.getId(), savedRole.isActive());
        return savedRole;
    }

    @Transactional
    public Role updateRole(Long roleId, Role roleUpdate) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.ROLE_NOT_FOUND));

        if (roleUpdate.getName() != null) {
            existingRole.setName(roleUpdate.getName());
        }
        if (roleUpdate.getExpirationDatetime() != null) {
            existingRole.setExpirationDatetime(roleUpdate.getExpirationDatetime());
        }
        if (roleUpdate.getPlanet() != null) {
            existingRole.setPlanet(roleUpdate.getPlanet());
        }
        if (roleUpdate.getFlight() != null) {
            existingRole.setFlight(roleUpdate.getFlight());
        }

        validateAndSetRoleStatus(existingRole);

        Role savedRole = roleRepository.save(existingRole);
        log.debug("Updated role id={}, active={}", savedRole.getId(), savedRole.isActive());
        return savedRole;
    }

    private void validateAndSetRoleStatus(Role role) {
        if (role.getExpirationDatetime() != null &&
                role.getExpirationDatetime().isBefore(Instant.now())) {

            if (role.isActive()) {
                role.setActive(false);
                log.info("Role id={} deactivated automatically - expiration date passed: {}",
                        role.getId(), role.getExpirationDatetime());
            }
        }
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void checkAllRolesForExpiration() {
        log.info("Starting scheduled check for expired roles");

        Instant now = Instant.now();

        List<Role> activeRoles = roleRepository.findByActiveTrue();
        int deactivatedCount = 0;

        for (Role role : activeRoles) {
            if (role.getExpirationDatetime() != null &&
                    role.getExpirationDatetime().isBefore(now)) {

                role.setActive(false);
                roleRepository.save(role);
                deactivatedCount++;

                log.info("Scheduled deactivation: Role id={} expired at {}",
                        role.getId(), role.getExpirationDatetime());
            }
        }

        if (deactivatedCount > 0) {
            log.info("Scheduled check completed: deactivated {} expired roles", deactivatedCount);
        } else {
            log.debug("Scheduled check completed: no expired roles found");
        }
    }

    @Transactional(readOnly = true)
    public Role getValidRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.ROLE_NOT_FOUND));

        if (!role.isActive() && role.getExpirationDatetime() != null &&
                role.getExpirationDatetime().isBefore(Instant.from(LocalDateTime.now()))) {
            log.warn("Accessed expired role id={}", roleId);
        }

        return role;
    }

    @Transactional(readOnly = true)
    public List<Role> getActiveRoles() {
        Instant now = Instant.now();
        return roleRepository.findByActiveTrue().stream()
                .filter(role ->
                        role.getExpirationDatetime() == null ||
                                !role.getExpirationDatetime().isBefore(now))
                .toList();
    }
}