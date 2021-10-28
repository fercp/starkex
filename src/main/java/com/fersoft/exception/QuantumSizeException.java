package com.fersoft.exception;

/**
 * Exception to quantum size violation
 *
 * @author ferat capar
 */
public class QuantumSizeException extends StarkException {
    public QuantumSizeException(Object... params) {
        super("quantumSize", params);
    }
}
