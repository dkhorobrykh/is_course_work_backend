package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.dto.ChatDto;
import ru.itmo.is.course_work.model.dto.NewMessageDto;
import ru.itmo.is.course_work.model.mapper.ChatMapper;
import ru.itmo.is.course_work.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
@Tag(
        name = "Chat controller",
        description = "Контроллер для взаимодействия с системой чатов"
)
public class ChatController {

    private final ChatMapper chatMapper;
    private final ChatService chatService;

    @GetMapping
    @Operation(summary = "Получить список чатов для текущего пользователя")
    public ResponseEntity<List<ChatDto>> getAllChatsForCurrentUser() {
        var result = chatService.getAllChatsForCurrentUser();

        return ResponseEntity.ok(chatMapper.toDto(result));
    }

    @PostMapping("{chatId}/send")
    @Operation(summary = "Отправить сообщение в чат [{chatId}] от текущего пользователя")
    public ResponseEntity<ChatDto> sendMessage(@PathVariable Long chatId, @RequestBody NewMessageDto dto) {
        var result = chatService.sendMessage(chatId, dto);

        return ResponseEntity.ok(chatMapper.toDto(result));
    }

    @PostMapping("{secondUserId}")
    @Operation(summary = "Создать чат между текущим пользователем и каким-то другим")
    public ResponseEntity<ChatDto> createChat(@PathVariable Long secondUserId) {
        var result = chatService.createChat(secondUserId);

        return ResponseEntity.ok(chatMapper.toDto(result));
    }
}
