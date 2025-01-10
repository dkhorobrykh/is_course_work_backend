package ru.itmo.is.course_work.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Role;
import ru.itmo.is.course_work.model.dto.RoleAddDto;
import ru.itmo.is.course_work.model.dto.RoleEditDto;
import ru.itmo.is.course_work.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleAssignService {

    private final RoleRepository roleRepository;
    private final FlightService flightService;
    private final PlanetService planetService;
    private final UserService userService;

    public List<Role> getAllRoles() {
        return roleRepository.findAllByOrderById();
    }

    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.ROLE_NOT_FOUND));
    }

    public Role addRole(@Valid RoleAddDto dto) {

        var name = dto.getName();

        var flight = dto.getFlightId() != null
                ? flightService.getFlightById(dto.getFlightId())
                : null;

        var planet = dto.getPlanetId() != null
                ? planetService.getPlanetById(dto.getPlanetId())
                : null;

        var active = Optional.ofNullable(dto.getActive()).orElse(false);

        var expirationDatetime = dto.getExpirationDatetime();

        var newRole = Role.builder()

                .name(name)
                .flight(flight)
                .planet(planet)
                .active(active)
                .expirationDatetime(expirationDatetime)

                .build();

        return roleRepository.saveAndFlush(newRole);
    }

    public void addRoleToUser(Long roleId, Long userId) {
        var user = userService.getById(userId);
        var role = getRoleById(roleId);

        user.getRoles().add(role);
        userService.save(user);
    }

    public void deleteRoleFromUser(Long roleId, Long userId) {
        var user = userService.getById(userId);
        var role = getRoleById(roleId);

        user.setRoles(user.getRoles().stream().filter(x -> !x.getId().equals(role.getId())).collect(Collectors.toSet()));

        userService.save(user);
    }

    public void editRole(Long roleId, @Valid RoleEditDto dto) {
        var role = getRoleById(roleId);

        var active = dto.getActive() != null
                ? dto.getActive()
                : null;

        var expirationDatetime = dto.getExpirationDatetime() != null
                ? dto.getExpirationDatetime()
                : null;

        if (active != null)
            role.setActive(active);

        if (expirationDatetime != null)
            role.setExpirationDatetime(expirationDatetime);

        roleRepository.saveAndFlush(role);
    }
}
