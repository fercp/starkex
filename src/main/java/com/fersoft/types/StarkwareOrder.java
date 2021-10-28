package com.fersoft.types;

import java.math.BigInteger;


public record StarkwareOrder(StarkwareAmounts starkwareAmounts,
                             StarkwareOrderType orderType, BigInteger quantumsAmountFee,
                             BigInteger assetIdFee, String positionId, BigInteger nonce,
                             Integer expirationEpochHours) {


}
