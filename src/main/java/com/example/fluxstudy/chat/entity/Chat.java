package com.example.fluxstudy.chat.entity;

import com.example.fluxstudy.chat.dto.ChatDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Chat {
    @Id
    private String id;
    private String message;
    private String sender;
    private String receiver;
    @CreatedDate
    private LocalDateTime createdAt;

    public static Chat of(ChatDto dto) {
        return Chat.builder()
                .id(dto.getId())
                .message(dto.getMessage())
                .receiver(dto.getReceiver())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
