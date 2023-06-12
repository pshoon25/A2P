package com.A2P.dev.exception;

public class AdminLoginException extends Exception {
    public AdminLoginException() {
        super("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
}
