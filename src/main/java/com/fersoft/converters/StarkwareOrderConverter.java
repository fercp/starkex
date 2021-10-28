package com.fersoft.converters;

import com.fersoft.exception.QuantumSizeException;
import com.fersoft.types.DydxAsset;
import com.fersoft.types.NetworkId;
import com.fersoft.types.OrderWithClientId;
import com.fersoft.types.OrderWithClientIdAndQuoteAmount;
import com.fersoft.types.OrderWithClientIdWithPrice;
import com.fersoft.types.OrderWithNonce;
import com.fersoft.types.OrderWithNonceAndPrice;
import com.fersoft.types.OrderWithNonceAndQuoteAmount;
import com.fersoft.types.StarkwareAmounts;
import com.fersoft.types.StarkwareOrder;
import com.fersoft.types.StarkwareOrderSide;
import com.fersoft.types.StarkwareOrderType;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;

/**
 * Converts OrderWithClientId objects into StarkwareOrders
 *
 * @author ferat capar
 */
public class StarkwareOrderConverter {
    private static final Logger logger = LoggerFactory.getLogger(StarkwareOrderConverter.class);
    private static final double ONE_HOUR_IN_SECONDS = 60 * 60.0;
    private static final Integer STARK_ORDER_SIGNATURE_EXPIRATION_BUFFER_HOURS = 24 * 7;
    private static final BigInteger MAX_NONCE = BigInteger.TWO.pow(32);

    /**
     * Creates StarkwareOrder from an OrderWithClientId and networkId
     *
     * @param order     with type OrderWithClientId
     * @param networkId
     * @return starkware order
     * @throws NoSuchAlgorithmException in case of hash algorithm fails
     * @throws QuantumSizeException
     * @see StarkwareOrder,OrderWithClientId,NetworkId
     */
    public StarkwareOrder fromOrderWithClientId(OrderWithClientId order, NetworkId networkId) throws NoSuchAlgorithmException, QuantumSizeException {
        BigInteger nonce = nonceFromClientId(order.getClientId());
        logger.trace("Generated nonce: {} from client id:{}", nonce, order.getClientId());
        if (order instanceof OrderWithClientIdWithPrice orderWithPrice) {
            return fromOrderWithNonce(new OrderWithNonceAndPrice(order.getOrder(), nonce, orderWithPrice.getHumanPrice()), networkId);
        }
        return fromOrderWithNonce(new OrderWithNonceAndQuoteAmount(order.getOrder(), nonce, ((OrderWithClientIdAndQuoteAmount) order).getHumanQuoteAmount()), networkId);
    }

    /**
     * Creates a nonce from clientId
     *
     * @param clientId
     * @return nonce
     * @throws NoSuchAlgorithmException in case of hashing algorithm(sha256) not found
     */
    public BigInteger nonceFromClientId(String clientId) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(clientId.getBytes());
        String sha256hex = new String(Hex.encode(hash));
        return new BigInteger(sha256hex, 16).mod(MAX_NONCE);
    }

    /**
     * Creates StarkwareOrder from OrderWithNonce and networkId
     *
     * @param order
     * @param networkId
     * @return StarkwareOrder object
     * @throws QuantumSizeException
     * @see OrderWithNonce,StarkwareOrder,NetworkId
     */
    public StarkwareOrder fromOrderWithNonce(OrderWithNonce order, NetworkId networkId) throws QuantumSizeException {
        // Need to be careful that the (size, price) -> (amountBuy, amountSell) function is
        // well-defined and applied consistently.
        StarkwareAmounts starkwareAmounts = getStarkwareAmounts(order, networkId);

        // The limitFee is a fraction, e.g. 0.01 is a 1% fee. It is always paid in the collateral asset.
        BigInteger quantumsAmountFee = getStarkwareLimitFeeAmount(order.getOrder().limitFee(), starkwareAmounts.quantumsAmountCollateral());

        // Convert to a Unix timestamp (in hours) and add buffer to ensure signature is valid on-chain.
        Integer expirationEpochHours =
                isoTimestampToEpochHours(order.getOrder().expirationIsoTimestamp()) + STARK_ORDER_SIGNATURE_EXPIRATION_BUFFER_HOURS;
        logger.trace("expirationEpochHours {} from {}", expirationEpochHours, order.getOrder().expirationIsoTimestamp());
        return
                new StarkwareOrder(
                        starkwareAmounts,
                        StarkwareOrderType.LIMIT_ORDER_WITH_FEES,
                        quantumsAmountFee,
                        starkwareAmounts.assetIdCollateral(),
                        order.getOrder().positionId(),
                        order.getNonce(),
                        expirationEpochHours
                );
    }

    /**
     * Creates StarkwareAmounts from and order (OrderWithNonce) and networkdId
     *
     * @param order
     * @param networkId
     * @return StarkwareAmounts object
     * @throws QuantumSizeException
     */
    public StarkwareAmounts getStarkwareAmounts(OrderWithNonce order, NetworkId networkId) throws QuantumSizeException {
        DydxAsset syntheticAsset = order.getOrder().market().getAsset();

        // Convert the synthetic amount to Starkware quantums.
        BigInteger quantumsAmountSynthetic = order.toQuantums(order.getOrder().humanSize(), syntheticAsset, RoundingMode.DOWN, true);
        logger.trace("quantumsAmountSynthetic {} from humanSize {} ,syntheticAsset {} ,rounding mode {} ", quantumsAmountSynthetic, order.getOrder().humanSize(), syntheticAsset, RoundingMode.DOWN);
        return new StarkwareAmounts(
                quantumsAmountSynthetic,
                order.toQuantums(),
                syntheticAsset.getAssetId(),
                networkId.getCollateralAddressId(),
                order.getOrder().side() == StarkwareOrderSide.BUY
        );
    }


    private BigInteger getStarkwareLimitFeeAmount(String limitFee, BigInteger quantumsAmountCollateral) {
        // Constrain the limit fee to six decimals of precision. The final fee amount must be rounded up.
        return new BigDecimal(limitFee)
                .round(new MathContext(6, RoundingMode.DOWN))
                .multiply(new BigDecimal(quantumsAmountCollateral))
                .round(new MathContext(0, RoundingMode.UP))
                .toBigInteger();
    }

    private Integer isoTimestampToEpochHours(String isoTimestamp) {
        return (int) Math.ceil(ZonedDateTime.parse(isoTimestamp).toEpochSecond() / ONE_HOUR_IN_SECONDS);
    }
}
