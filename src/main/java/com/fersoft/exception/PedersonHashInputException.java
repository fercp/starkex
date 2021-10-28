package com.fersoft.exception;

/**
 * Exception to input violation of pederson hash function
 *
 * @author ferat capar
 */
public class PedersonHashInputException extends HashingException{
    public PedersonHashInputException(Object... params) {
        super("pedersonHashInput", params);
    }
}
