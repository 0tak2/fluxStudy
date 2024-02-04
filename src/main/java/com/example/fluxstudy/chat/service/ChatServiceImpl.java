package com.example.fluxstudy.chat.service;

import com.example.fluxstudy.chat.dto.ChatDto;
import com.example.fluxstudy.chat.entity.Chat;
import com.example.fluxstudy.chat.exception.DomainException;
import com.example.fluxstudy.chat.exception.ErrorDetail;
import com.example.fluxstudy.chat.repository.ChatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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

        return chatRepository.save(Chat.of(chat)).map(chatEntity -> {
            return ChatDto.of(chatEntity);
        });
    }

    @Override
    public Flux<ChatDto> getChatByDate(LocalDateTime start, LocalDateTime end) {
        log.info("start: " + start);
        log.info("end: " + end);
        return chatRepository.findByCreatedAtBetween(start, end)
                .map(chatEntity -> ChatDto.of(chatEntity));
    }
}
