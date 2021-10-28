package com.fersoft.exception;

public class SignException extends StarkException{
    public SignException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
