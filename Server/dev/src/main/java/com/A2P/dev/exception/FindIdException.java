package com.A2P.dev.exception;

public class FindIdException extends Exception{
    public FindIdException() {
        super("존재하지 않는 정보입니다. 다시 확인해주세요.");
    }
}
