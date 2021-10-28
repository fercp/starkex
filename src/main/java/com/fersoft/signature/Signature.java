package com.fersoft.signature;

import java.math.BigInteger;

/**
 * ECDSA signature representation
 *
 * @author ferat capar
 */
public record Signature (BigInteger r,BigInteger s){

    @Override
    public String toString() {
        return String.format("%1$64s%2$64s", this.r.toString(16), this.s.toString(16)).replace(" ", "0");
    }

}
