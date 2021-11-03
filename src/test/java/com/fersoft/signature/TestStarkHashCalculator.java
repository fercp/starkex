package com.fersoft.signature;

import com.fersoft.converters.StarkwareOrderConverter;
import com.fersoft.exception.FieldExceedMaxException;
import com.fersoft.exception.HashingException;
import com.fersoft.exception.QuantumSizeException;
import com.fersoft.hashing.ConstantPoints;
import com.fersoft.hashing.PedersonHash;
import com.fersoft.hashing.StarkHashCalculator;
import com.fersoft.types.DydxMarket;
import com.fersoft.types.NetworkId;
import com.fersoft.types.Order;
import com.fersoft.types.OrderWithClientId;
import com.fersoft.types.OrderWithClientIdAndQuoteAmount;
import com.fersoft.types.OrderWithClientIdWithPrice;
import com.fersoft.types.StarkwareOrder;
import com.fersoft.types.StarkwareOrderSide;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TestStarkHashCalculator {
    private final static String HASH_VALUE = "1690222932b6b9f7ec1a92f3950e5332892789e7531336114f588ed08de3a42";
    private final static StarkwareOrderConverter STARKWARE_ORDER_CONVERTER = new StarkwareOrderConverter();
    private final static StarkHashCalculator STARK_HASH_CALCULATOR=new StarkHashCalculator(new PedersonHash(ConstantPoints.POINTS.get(0)));
    private final static OrderWithClientIdWithPrice order = new OrderWithClientIdWithPrice(
            new Order("56277",
                    "1",
                    "0.001",
                    DydxMarket.ATOM_USD,
                    StarkwareOrderSide.BUY,
                    "2021-09-20T00:00:00.000Z"),
            "123456",
            "34.00"
    );

    @Test
    void testHashWithPrice() throws Exception {
        StarkwareOrder starkwareOrder = STARKWARE_ORDER_CONVERTER.fromOrderWithClientId(order, NetworkId.ROPSTEN);
        assertThat(STARK_HASH_CALCULATOR.calculateHash(starkwareOrder).toString(16), is(equalTo(HASH_VALUE)));
    }

    @Test
    void testHashWithQuateAmount() throws QuantumSizeException, NoSuchAlgorithmException, HashingException, FieldExceedMaxException {
        OrderWithClientIdAndQuoteAmount order = new OrderWithClientIdAndQuoteAmount(
                new Order("56277",
                        "1",
                        "0.001",
                        DydxMarket.ATOM_USD,
                        StarkwareOrderSide.BUY,
                        "2021-09-20T00:00:00.000Z"),
                "123456",
                "34.00"
        );

        StarkwareOrder starkwareOrder = STARKWARE_ORDER_CONVERTER.fromOrderWithClientId(order, NetworkId.ROPSTEN);
        assertThat(STARK_HASH_CALCULATOR.calculateHash(starkwareOrder).toString(16), is(equalTo(HASH_VALUE)));
    }

    @Test
    void testNonce() throws NoSuchAlgorithmException {
        assertThat(STARKWARE_ORDER_CONVERTER.nonceFromClientId("123456"),is(equalTo(new BigInteger("987524242"))));
    }

    @Test
    void test2() throws QuantumSizeException, NoSuchAlgorithmException, FieldExceedMaxException, HashingException {
        Order order = new Order("12345", "145.0005", "0.125", DydxMarket.ETH_USD, StarkwareOrderSide.BUY, "2020-09-17T04:15:55.028Z");
        OrderWithClientId orderWithClientID = new OrderWithClientIdWithPrice(order,
                "This is an ID that the client came up with to describe this order", "350.00067");
        StarkwareOrder starkwareOrder = STARKWARE_ORDER_CONVERTER.fromOrderWithClientId(orderWithClientID, NetworkId.ROPSTEN);
        assertThat(STARK_HASH_CALCULATOR.calculateHash(starkwareOrder).toString(16), is(equalTo("54defe3d7784789849556377433b4160f9eecd0ebb450cf3cdc02cb948abf48")));

    }

}
