package com.example.fluxstudy.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DomainException extends RuntimeException {
    ErrorDetail errorDetail;
}
