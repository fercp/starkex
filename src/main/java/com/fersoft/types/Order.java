package com.fersoft.types;

public record Order(
        String positionId,
        String humanSize,
        String limitFee, // Max fee fraction, e.g. 0.01 is a max 1% fee.
        DydxMarket market,
        StarkwareOrderSide side,
        String expirationIsoTimestamp) {

}
