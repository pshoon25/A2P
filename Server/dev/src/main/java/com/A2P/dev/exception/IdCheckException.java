package com.A2P.dev.exception;

public class IdCheckException extends Exception {
    public IdCheckException() {
        super("이미 등록된 아이디 입니다.");
    }
}
