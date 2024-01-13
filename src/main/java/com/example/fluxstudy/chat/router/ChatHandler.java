package com.example.fluxstudy.chat.router;

import com.example.fluxstudy.chat.dto.BasicResponse;
import com.example.fluxstudy.chat.dto.ChatDto;
import com.example.fluxstudy.chat.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ChatHandler {
    private final ChatService chatService;

    public Mono<ServerResponse> newChat(ServerRequest request) {
        return request.bodyToMono(ChatDto.class)
                .flatMap(chatService::addChat)
                .then(
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(BasicResponse.of(true)))
                );
    }
}
