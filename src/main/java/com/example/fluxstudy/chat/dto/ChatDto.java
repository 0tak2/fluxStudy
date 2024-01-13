package com.example.fluxstudy.chat.dto;

import com.example.fluxstudy.chat.entity.Chat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatDto {
    private String id;
    private String message;
    private String sender;
    private String receiver;
    private LocalDateTime createdAt;

    public static ChatDto of(Chat entity) {
        return ChatDto.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .sender(entity.getSender())
                .receiver(entity.getReceiver())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
