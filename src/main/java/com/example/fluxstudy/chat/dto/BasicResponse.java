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
public class BasicResponse<T> {
    private Boolean success;
    private Integer code;
    private String message;
    private T data;

    public static BasicResponse<Object> of(boolean isSuccess) {
        return BasicResponse.<Object>builder()
                .success(isSuccess)
                .build();
    }

    public static BasicResponse<Object> of(DomainException ex) {
        return BasicResponse.<Object>builder()
                .success(false)
                .code(ex.getErrorDetail().getCode())
                .message(ex.getErrorDetail().getMessage())
                .build();
    }

    public static <T> BasicResponse<T> of(boolean isSuccess, T data) {
        return BasicResponse.<T>builder()
                .success(isSuccess)
                .data(data)
                .build();
    }
}
