package ru.itmo.is.course_work.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ExceptionEnum;
import ru.itmo.is.course_work.model.Chat;
import ru.itmo.is.course_work.model.Message;
import ru.itmo.is.course_work.model.dto.NewMessageDto;
import ru.itmo.is.course_work.repository.ChatRepository;
import ru.itmo.is.course_work.repository.MessageRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

    public List<Chat> getAllChatsForCurrentUser() {
        var currentUser = RoleService.getCurrentUser();

        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        return chatRepository.findAllByUserFirst_IdOrUserSecond_Id(currentUser.getId(), currentUser.getId());
    }

    public Chat sendMessage(Long chatId, @Valid NewMessageDto dto) {
        var currentUser = RoleService.getCurrentUser();

        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        var chat = getChatById(chatId);

        if (!Objects.equals(chat.getUserFirst().getId(), currentUser.getId()) && !Objects.equals(chat.getUserSecond().getId(), currentUser.getId()))
            throw new CustomException(ExceptionEnum.FORBIDDEN);

        var newMessage = Message.builder()

                .chat(chat)
                .text(dto.getText())

                .build();

        messageRepository.saveAndFlush(newMessage);

        return getChatById(chatId);
    }

    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(() -> new CustomException(ExceptionEnum.CHAT_NOT_FOUND));
    }

    public Chat createChat(Long secondUserId) {
        var currentUser = RoleService.getCurrentUser();

        if (currentUser == null)
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);

        var secondUser = userService.getById(secondUserId);

        var maybeExistingChat = findChatByUserIds(currentUser.getId(), secondUser.getId());
        if (maybeExistingChat.isPresent())
            return maybeExistingChat.get();

        if (currentUser.getId().equals(secondUser.getId()))
            throw new CustomException(ExceptionEnum.CHAT_WITH_THE_SAME_USER);

        var newChat = Chat.builder()

                .userFirst(currentUser)
                .userSecond(secondUser)

                .build();

        return chatRepository.saveAndFlush(newChat);
    }

    public Optional<Chat> findChatByUserIds(Long firstUserId, Long secondUserId) {
        var res = chatRepository.findByUserFirst_IdAndUserSecond_Id(firstUserId, secondUserId);

        if (res.isEmpty())
            res = chatRepository.findByUserFirst_IdAndUserSecond_Id(secondUserId, firstUserId);

        return res;
    }
}
