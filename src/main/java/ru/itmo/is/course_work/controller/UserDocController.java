package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.UserDocAddDto;
import ru.itmo.is.course_work.model.dto.UserDocDto;
import ru.itmo.is.course_work.model.dto.UserDocTypeDto;
import ru.itmo.is.course_work.model.mapper.UserDocMapper;
import ru.itmo.is.course_work.model.mapper.UserDocTypeMapper;
import ru.itmo.is.course_work.service.UserDocService;

import java.util.List;

@RestController
@RequestMapping("user/docs")
@RequiredArgsConstructor
@Tag(
        name = "User docs controller",
        description = "Контроллер для взаимодействия с системой документов пользователя"
)
public class UserDocController {

    private final UserDocMapper userDocMapper;
    private final UserDocService userDocService;
    private final UserDocTypeMapper userDocTypeMapper;

    @GetMapping
    @Operation(summary = "Получить список существующих документов пользователя")
    public ResponseEntity<List<UserDocDto>> getAllDocsForCurrentUser() {
        var result = userDocService.getAllDocsForCurrentUser();

        return ResponseEntity.ok(userDocMapper.toDto(result));
    }

    @PostMapping
    @Operation(summary = "Добавить документ текущему пользователю")
    public ResponseEntity<List<UserDocDto>> addNewDoc(@RequestBody @Valid UserDocAddDto dto) {
        userDocService.addNewDoc(dto);
        var docs = userDocService.getAllDocsForCurrentUser();

        return ResponseEntity.ok(userDocMapper.toDto(docs));
    }

    @DeleteMapping("{documentId}")
    @PreAuthorize("@RoleService.hasAdminRole() || @RoleService.userIdEqualsCurrent(@userDocService.getUserDocById(#documentId).user.id)")
    @Operation(summary = "Удалить документ по его [{documentId}]")
    public ResponseEntity<List<UserDocDto>> deleteDocById(@PathVariable Long documentId) {
        userDocService.deleteDocById(documentId);
        var docs = userDocService.getAllDocsForCurrentUser();

        return ResponseEntity.ok(userDocMapper.toDto(docs));
    }

    @GetMapping("types")
    @Operation(summary = "Получить список доступных типов документов")
    public ResponseEntity<List<UserDocTypeDto>> getAvailableDocTypes() {
        var result = userDocService.getAllUserDocTypes();

        return ResponseEntity.ok(userDocTypeMapper.toDto(result));
    }
}
