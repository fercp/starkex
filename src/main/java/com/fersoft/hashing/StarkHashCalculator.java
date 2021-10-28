package com.fersoft.hashing;

import com.fersoft.exception.FieldExceedMaxException;
import com.fersoft.exception.HashingException;
import com.fersoft.types.StarkwareOrder;
import java.math.BigInteger;

/**
 * calculates hash using given hash function
 * input message is StarkwareOrder
 *
 * @author ferat capar
 * @see StarkwareOrder,HashFunction
 */
public class StarkHashCalculator implements HashCalculator<StarkwareOrder> {
    private static final String LIMIT_ORDER_WITH_FEES = "3";
    private static final int ORDER_PADDING_BITS = 17;
    private final HashFunction hashFunction;


    public StarkHashCalculator(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    @Override
    public BigInteger calculateHash(StarkwareOrder message) throws FieldExceedMaxException, HashingException {
        BigInteger positionIdBn = new BigInteger(message.positionId());
        BigInteger expirationEpochHours = new BigInteger(message.expirationEpochHours().toString());

        BigInteger assetIdSellBn;
        BigInteger assetIdBuyBn;
        BigInteger quantumsAmountSellBn;
        BigInteger quantumsAmountBuyBn;

        if (message.starkwareAmounts().isBuyingSynthetic()) {
            assetIdSellBn = message.starkwareAmounts().assetIdCollateral();
            assetIdBuyBn = message.starkwareAmounts().assetIdSynthetic();
            quantumsAmountSellBn = message.starkwareAmounts().quantumsAmountCollateral();
            quantumsAmountBuyBn = message.starkwareAmounts().quantumsAmountSynthetic();
        } else {
            assetIdSellBn = message.starkwareAmounts().assetIdSynthetic();
            assetIdBuyBn = message.starkwareAmounts().assetIdCollateral();
            quantumsAmountSellBn = message.starkwareAmounts().quantumsAmountSynthetic();
            quantumsAmountBuyBn = message.starkwareAmounts().quantumsAmountCollateral();
        }

        checkFieldSizes(message, message.assetIdFee(), message.quantumsAmountFee(), message.nonce(), positionIdBn, expirationEpochHours);

        BigInteger orderPart1 = new BigInteger(quantumsAmountSellBn.toString())
                .shiftLeft(OrderFieldBitLengths.QUANTUMS_AMOUNT).add(quantumsAmountBuyBn)
                .shiftLeft(OrderFieldBitLengths.QUANTUMS_AMOUNT).add(message.quantumsAmountFee())
                .shiftLeft(OrderFieldBitLengths.NONCE).add(message.nonce());

        BigInteger orderPart2 = new BigInteger(LIMIT_ORDER_WITH_FEES)
                .shiftLeft(OrderFieldBitLengths.POSITION_ID).add(positionIdBn) // Repeat (1/3).
                .shiftLeft(OrderFieldBitLengths.POSITION_ID).add(positionIdBn) // Repeat (2/3).
                .shiftLeft(OrderFieldBitLengths.POSITION_ID).add(positionIdBn) // Repeat (3/3).
                .shiftLeft(OrderFieldBitLengths.EXPIRATION_EPOCH_HOURS).add(expirationEpochHours)
                .shiftLeft(ORDER_PADDING_BITS);

        BigInteger cacheAsset = hashFunction.hashFromCache(assetIdSellBn, assetIdBuyBn);
        BigInteger assetsBn = hashFunction.hashFromCache(cacheAsset, message.assetIdFee());

        return hashFunction.createHash(
                hashFunction.createHash(assetsBn, orderPart1),
                orderPart2
        );
    }

    private void checkFieldSizes(StarkwareOrder message, BigInteger assetIdFee, BigInteger quantumsAmountFee, BigInteger nonce, BigInteger positionId, BigInteger expirationEpochHours) throws FieldExceedMaxException {
        if (message.starkwareAmounts().assetIdSynthetic().bitLength() > OrderFieldBitLengths.ASSET_ID_SYNTHETIC) {
            throw new FieldExceedMaxException("assetIdSynthetic");
        }
        if (message.starkwareAmounts().assetIdCollateral().bitLength() > OrderFieldBitLengths.ASSET_ID_COLLATERAL) {
            throw new FieldExceedMaxException("assetIdCollateral");
        }
        if (assetIdFee.bitLength() > OrderFieldBitLengths.ASSET_ID_FEE) {
            throw new FieldExceedMaxException("assetIdFee");
        }
        if (message.starkwareAmounts().quantumsAmountSynthetic().bitLength() > OrderFieldBitLengths.QUANTUMS_AMOUNT) {
            throw new FieldExceedMaxException("quantumsAmountSynthetic");
        }
        if (message.starkwareAmounts().quantumsAmountCollateral().bitLength() > OrderFieldBitLengths.QUANTUMS_AMOUNT) {
            throw new FieldExceedMaxException("quantumsAmountCollateral");
        }
        if (quantumsAmountFee.bitLength() > OrderFieldBitLengths.QUANTUMS_AMOUNT) {
            throw new FieldExceedMaxException("quantumsAmountFee");
        }
        if (nonce.bitLength() > OrderFieldBitLengths.NONCE) {
            throw new FieldExceedMaxException("nonce");
        }
        if (positionId.bitLength() > OrderFieldBitLengths.POSITION_ID) {
            throw new FieldExceedMaxException("positionId");
        }
        if (expirationEpochHours.bitLength() > OrderFieldBitLengths.EXPIRATION_EPOCH_HOURS) {
            throw new FieldExceedMaxException("expirationEpochHours");
        }
    }


    static class OrderFieldBitLengths {
        public static final int ASSET_ID_SYNTHETIC = 128;
        public static final int ASSET_ID_COLLATERAL = 250;
        public static final int ASSET_ID_FEE = 250;
        public static final int QUANTUMS_AMOUNT = 64;
        public static final int NONCE = 32;
        public static final int POSITION_ID = 64;
        public static final int EXPIRATION_EPOCH_HOURS = 32;

        private OrderFieldBitLengths() {
        }
    }
}
