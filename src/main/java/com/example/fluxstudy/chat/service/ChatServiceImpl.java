package com.example.fluxstudy.chat.service;

import com.example.fluxstudy.chat.dto.ChatDto;
import com.example.fluxstudy.chat.entity.Chat;
import com.example.fluxstudy.chat.exception.DomainException;
import com.example.fluxstudy.chat.exception.ErrorDetail;
import com.example.fluxstudy.chat.repository.ChatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public Mono<ChatDto> addChat(ChatDto chat) {
        if (chat.getId() != null) {
            throw new DomainException(ErrorDetail.BAD_REQUEST);
        }

        return chatRepository.insert(Chat.of(chat)).map(chatEntity -> {
            return ChatDto.of(chatEntity);
        });
    }
}
