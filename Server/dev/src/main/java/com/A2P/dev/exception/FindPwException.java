package com.A2P.dev.exception;

public class FindPwException extends Exception{
    public FindPwException() {
        super("계정을 찾을 수 없습니다. 정보를 다시 확인해주세요.");
    }
}
