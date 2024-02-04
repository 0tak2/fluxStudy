package com.example.fluxstudy.chat.repository;

import com.example.fluxstudy.chat.entity.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    Flux<Chat> findByCreatedAtBetween(LocalDateTime createdAtGt, LocalDateTime createdAtLt);
}
