package com.example.fluxstudy.chat.repository;

import com.example.fluxstudy.chat.entity.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
}
