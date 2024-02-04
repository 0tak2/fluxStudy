package com.example.fluxstudy.chat.service;

import com.example.fluxstudy.chat.dto.ChatDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface ChatService {
    public Mono<ChatDto> addChat(ChatDto chat);

    Flux<ChatDto> getChatByDate(LocalDateTime start, LocalDateTime end);
}
