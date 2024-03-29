package com.example.fluxstudy.chat.router;

import com.example.fluxstudy.chat.dto.BasicResponse;
import com.example.fluxstudy.chat.dto.ChatDto;
import com.example.fluxstudy.chat.exception.DomainException;
import com.example.fluxstudy.chat.exception.ErrorDetail;
import com.example.fluxstudy.chat.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class ChatHandler {
    private final ChatService chatService;
    private final DirectProcessor<ServerSentEvent<ChatDto>> eventProcessor = DirectProcessor.create();

    public Mono<ServerResponse> newChat(ServerRequest request) {
        return request.bodyToMono(ChatDto.class)
                .flatMap(chatService::addChat)
                .map(chat -> ServerSentEvent.<ChatDto>builder()
                        .id(chat.getId())
                        .event("new-chat")
                        .data(chat)
                        .build())
                .doOnNext(sse -> eventProcessor.onNext(sse))
                .then(
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(BasicResponse.of(true)))
                );
    }

    public Mono<ServerResponse> getChatByTime(ServerRequest request) {
        Flux<ChatDto> result = chatService.getChatByDate(LocalDateTime.parse(
                request.queryParam("start").orElseThrow(() -> new DomainException(ErrorDetail.BAD_REQUEST)), DateTimeFormatter.ISO_DATE_TIME
        ), LocalDateTime.parse(
                request.queryParam("end").orElseThrow(() -> new DomainException(ErrorDetail.BAD_REQUEST)), DateTimeFormatter.ISO_DATE_TIME
        ));

        return result.collectList()
                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(BasicResponse.of(true, data))));
    }

    public Mono<ServerResponse> sseConnect(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(Flux.concat(Flux.just(new ChatDto()), eventProcessor), ChatDto.class);
    }
}
