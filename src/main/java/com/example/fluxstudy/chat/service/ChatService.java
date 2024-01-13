package com.example.fluxstudy.chat.service;

import com.example.fluxstudy.chat.dto.ChatDto;
import reactor.core.publisher.Mono;

public interface ChatService {
    public Mono<ChatDto> addChat(ChatDto chat);
}
