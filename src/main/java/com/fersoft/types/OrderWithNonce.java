package com.fersoft.types;

import com.fersoft.exception.QuantumSizeException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public abstract class OrderWithNonce {
    private final Order order;
    private final BigInteger nonce;

    public OrderWithNonce(Order order, BigInteger nonce) {
        this.order = order;
        this.nonce = nonce;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public Order getOrder() {
        return order;
    }

    public BigInteger toQuantums() throws QuantumSizeException {
        return toQuantums(getHumanAmount(), DydxAsset.USDC, getRoundingMode(), throwException());
    }

    public BigInteger toQuantums(String humanAmount, DydxAsset asset, RoundingMode roundingMode, boolean throwException) throws QuantumSizeException {
        BigDecimal amount = new BigDecimal(humanAmount);
        BigDecimal quantumSize = BigDecimal.ONE.divide(BigDecimal.TEN.pow(asset.getResolution()));
        BigDecimal remainder = amount.remainder(quantumSize);
        if (throwException && remainder.compareTo(BigDecimal.ZERO) != 0) {
            throw new QuantumSizeException(humanAmount, quantumSize);
        }

        return amount.divide(quantumSize, roundingMode).setScale(0, roundingMode).toBigInteger();
    }

    protected abstract boolean throwException();

    protected abstract RoundingMode getRoundingMode();

    protected abstract String getHumanAmount();
}
