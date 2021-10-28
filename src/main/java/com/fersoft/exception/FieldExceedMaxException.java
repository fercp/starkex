package com.fersoft.exception;

/**
 * Exception to field size violation
 *
 * @author ferat capar
 */
public class FieldExceedMaxException extends StarkException {
    public FieldExceedMaxException(String fieldName) {
        super("fieldExceedMax", fieldName);
    }
}
