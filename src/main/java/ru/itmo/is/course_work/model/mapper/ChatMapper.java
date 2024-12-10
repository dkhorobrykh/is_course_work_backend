package ru.itmo.is.course_work.model.mapper;

import org.mapstruct.*;
import ru.itmo.is.course_work.model.Chat;
import ru.itmo.is.course_work.model.dto.ChatDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatMapper {
    Chat toEntity(ChatDto chatDto);

    @AfterMapping
    default void linkMessages(@MappingTarget Chat chat) {
        chat.getMessages().forEach(message -> message.setChat(chat));
    }

    ChatDto toDto(Chat chat);

    List<ChatDto> toDto(List<Chat> chat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Chat partialUpdate(ChatDto chatDto, @MappingTarget Chat chat);
}