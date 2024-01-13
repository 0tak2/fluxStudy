package com.example.fluxstudy.chat.dto;

import com.example.fluxstudy.chat.exception.DomainException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicResponse {
    private Boolean success;
    private Integer code;
    private String message;

    public static BasicResponse of(boolean isSuccess) {
        return BasicResponse.builder()
                .success(isSuccess)
                .build();
    }

    public static BasicResponse of(DomainException ex) {
        return BasicResponse.builder()
                .success(false)
                .code(ex.getErrorDetail().getCode())
                .message(ex.getErrorDetail().getMessage())
                .build();
    }
}
