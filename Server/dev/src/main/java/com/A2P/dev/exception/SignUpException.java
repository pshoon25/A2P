package com.A2P.dev.exception;

public class SignUpException extends Exception {
    public SignUpException() {
        super("등록에 실패하였습니다. 다시 시도해주세요.");
    }
}
