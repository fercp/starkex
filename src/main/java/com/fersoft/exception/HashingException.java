package com.fersoft.exception;

/**
 * Exception hash function
 *
 * @author ferat capar
 */
public class HashingException extends StarkException{
    public HashingException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
