package com.fersoft.types;

import java.math.BigInteger;

public record StarkwareAmounts(BigInteger quantumsAmountSynthetic,
                               BigInteger quantumsAmountCollateral, BigInteger assetIdSynthetic,
                               BigInteger assetIdCollateral, boolean isBuyingSynthetic) {

}
