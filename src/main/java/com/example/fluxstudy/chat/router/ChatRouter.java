package com.example.fluxstudy.chat.router;

import com.example.fluxstudy.chat.dto.BasicResponse;
import com.example.fluxstudy.chat.exception.DomainException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class ChatRouter {
    @Bean
    public RouterFunction<ServerResponse> chatRoute(ChatHandler chatHandler) {
        return RouterFunctions
                .route(POST("/chat").and(accept(MediaType.APPLICATION_JSON)), chatHandler::newChat)
                .andRoute(GET("/sse").and(accept(MediaType.TEXT_EVENT_STREAM)), chatHandler::sseConnect)
                .filter(handleError());
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> handleError() {
        return (request, next) -> next.handle(request)
                .onErrorResume(DomainException.class, e -> ServerResponse.badRequest().body(BodyInserters.fromValue(BasicResponse.of(e))));
    }
}
