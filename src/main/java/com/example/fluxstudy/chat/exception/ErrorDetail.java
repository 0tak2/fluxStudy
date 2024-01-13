package com.example.fluxstudy.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorDetail {
    //400XX BAD_REQUEST
    BAD_REQUEST(40000, "요청이 잘못되었습니다."),

    //404XX NOT_FOUND
    NOT_FOUND(40400, "요청한 자원을 찾을 수 없습니다."),
    USER_NOT_FOUND(40401, "요청한 유저 정보를 찾을 수 없습니다."),

    //409XX CONFLICT
    ALREADY_EXIST(40900, "이미 자원이 존재합니다."),
    USER_ALREADY_EXIST(40900, "이미 존재하는 아이디입니다."),

    //500XX INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(50000, "알 수 없는 오류입니다. 오류가 계속되면 관리자에게 문의하세요.");

    private final int code;
    private final String message;
}
