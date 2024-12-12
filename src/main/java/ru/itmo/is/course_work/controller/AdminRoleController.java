package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.dto.RoleAddDto;
import ru.itmo.is.course_work.model.dto.RoleDto;
import ru.itmo.is.course_work.model.dto.RoleEditDto;
import ru.itmo.is.course_work.model.mapper.RoleMapper;
import ru.itmo.is.course_work.service.RoleAssignService;

import java.util.List;

@RestController
@RequestMapping("/admin/role")
@RequiredArgsConstructor
@Tag(
        name = "Admin role controller",
        description = "Контроллер для взаимодействия с системами ролей"
)
public class AdminRoleController {

    private final RoleAssignService roleAssignService;
    private final RoleMapper roleMapper;

    @GetMapping
    @PreAuthorize("@RoleService.hasAdminRole()")
    @Operation(summary = "Получить существующие роли")
    public ResponseEntity<List<RoleDto>> getRoles() {
        var result = roleAssignService.getAllRoles();

        return ResponseEntity.ok(roleMapper.toDto(result));
    }

    @PostMapping
    @PreAuthorize("@RoleService.hasAdminRole()")
    @Operation(summary = "Добавить новую роль")
    public ResponseEntity<List<RoleDto>> addNewRole(@Valid @RequestBody RoleAddDto dto) {
        roleAssignService.addRole(dto);
        var result = roleAssignService.getAllRoles();

        return ResponseEntity.ok(roleMapper.toDto(result));
    }

    @PutMapping("{roleId}")
    @PreAuthorize("@RoleService.hasAdminRole()")
    @Operation(summary = "Изменить роль [{roleId}]")
    public ResponseEntity<List<RoleDto>> editRole(@PathVariable Long roleId, @Valid @RequestBody RoleEditDto dto) {
        roleAssignService.editRole(roleId, dto);
        var result = roleAssignService.getAllRoles();

        return ResponseEntity.ok(roleMapper.toDto(result));
    }

    @PostMapping("/add/{roleId}/to/{userId}")
    @PreAuthorize("@RoleService.hasAdminRole()")
    @Operation(summary = "Добавить роль [{roleId}] пользователю [{userId}]")
    public ResponseEntity<List<RoleDto>> addRoleToUser(@PathVariable Long roleId, @PathVariable Long userId) {
        roleAssignService.addRoleToUser(roleId, userId);
        var result = roleAssignService.getAllRoles();

        return ResponseEntity.ok(roleMapper.toDto(result));
    }

    @DeleteMapping("/delete/{roleId}/from/{userId}")
    @PreAuthorize("@RoleService.hasAdminRole()")
    @Operation(summary = "Убрать роль [{roleId}] у пользователя [{userId}]")
    public ResponseEntity<List<RoleDto>> deleteRoleFromUser(@PathVariable Long roleId, @PathVariable Long userId) {
        roleAssignService.deleteRoleFromUser(roleId, userId);
        var result = roleAssignService.getAllRoles();

        return ResponseEntity.ok(roleMapper.toDto(result));
    }
}
