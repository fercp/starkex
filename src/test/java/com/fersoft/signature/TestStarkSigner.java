package com.fersoft.signature;

import com.fersoft.exception.FieldExceedMaxException;
import com.fersoft.exception.HashingException;
import com.fersoft.exception.QuantumSizeException;
import com.fersoft.exception.SignException;
import com.fersoft.types.DydxMarket;
import com.fersoft.types.NetworkId;
import com.fersoft.types.Order;
import com.fersoft.types.OrderWithClientId;
import com.fersoft.types.OrderWithClientIdAndQuoteAmount;
import com.fersoft.types.OrderWithClientIdWithPrice;
import com.fersoft.types.StarkwareOrderSide;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TestStarkSigner {
    private static final String EXPECTED_SIGNATURE = "06bca455438f4e337e11dff3897d85fdd425be2eb51bd0db92f34527965934e7003376e6726048f2bb23a6f89e7a7528c8cd832493431dd6c685148517ec752f";
    private static final Order order = new Order("12345", "145.0005", "0.125", DydxMarket.ETH_USD, StarkwareOrderSide.BUY, "2020-09-17T04:15:55.028Z");
    private static final OrderWithClientId orderWithClientID = new OrderWithClientIdWithPrice(order,
            "This is an ID that the client came up with to describe this order", "350.00067");
    private final BigInteger PRIVATE_KEY = new BigInteger("07230d8f6fcba9afb8eea3aa67119b5a1bc117500186c384b5aaee85dafbb64c", 16);
    private final String privateKey = "58c7d5a90b1776bde86ebac077e053ed85b0f7164f53b080304a531947f46e3";
    private final String mockSignature = "00cecbe513ecdbf782cd02b2a5efb03e58d5f63d15f2b840e9bc0029af04e8dd" +
            "0090b822b16f50b2120e4ea9852b340f7936ff6069d02acca02f2ed03029ace5";

    @Test
    void testOrderWithClientIdAndQuoteAmount() throws QuantumSizeException, NoSuchAlgorithmException, FieldExceedMaxException, HashingException, SignException {
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

        StarkSigner starkSigner = new StarkSigner();
        Signature signature = starkSigner.sign(order, NetworkId.ROPSTEN, PRIVATE_KEY);
        assertThat(signature.toString(), is(equalTo(EXPECTED_SIGNATURE)));
    }

    @Test
    void testSignOrderWithClientIdWithPrice() throws QuantumSizeException, NoSuchAlgorithmException, FieldExceedMaxException, HashingException, SignException {
        OrderWithClientIdWithPrice order = new OrderWithClientIdWithPrice(
                new Order("123456",
                        "1.00",
                        "0.0015",
                        DydxMarket.ZEC_USD,
                        StarkwareOrderSide.SELL,
                        "2021-11-03T16:22:23Z"),
                "WyHPW57ZKGwcie18UbEBGcry2QervYgYSG1Fm6YG",
                "200.0"
        );
        StarkSigner starkSigner = new StarkSigner();
        Signature signature = starkSigner.sign(order, NetworkId.MAINNET, new BigInteger("515282606d04ca75bfda0b2f3f92f9ad591f0153ff79ccac99f0a61016a4c5", 16));
        assertThat(signature.toString(), is(equalTo("0121b9b648ee938ca403bb865ab26aa442edb9b7f2c40edf7f86aae0b9686429014cf84c77fe3701ea4516a9e77c31890a69ec72f1af0c64cada7cbf14313c58")));
    }

    @Test
    void testSingOrderOddY() throws QuantumSizeException, NoSuchAlgorithmException, FieldExceedMaxException, HashingException, SignException {
        StarkSigner starkSigner = new StarkSigner();
        Signature signature =
                starkSigner.sign(orderWithClientID, NetworkId.ROPSTEN, new BigInteger(privateKey, 16));
        assertThat(signature.toString(), is(equalTo(mockSignature)));
    }

    @Test
    void testSingOrderEvenY() throws QuantumSizeException, NoSuchAlgorithmException, FieldExceedMaxException, HashingException, SignException {
        StarkSigner starkSigner = new StarkSigner();
        Signature signature =
                starkSigner.sign(orderWithClientID, NetworkId.ROPSTEN, new BigInteger("65b7bb244e019b45a521ef990fb8a002f76695d1fc6c1e31911680f2ed78b84", 16));
        assertThat(signature.toString(), is(equalTo("00fc0756522d78bef51f70e3981dc4d1e82273f59cdac6bc31c5776baabae6ec" +
                "0158963bfd45d88a99fb2d6d72c9bbcf90b24c3c0ef2394ad8d05f9d3983443a")));
    }

    @Test
    void testOrderQuoteAmount() throws QuantumSizeException, NoSuchAlgorithmException, FieldExceedMaxException, HashingException, SignException {
        OrderWithClientId orderWithClientID = new OrderWithClientIdAndQuoteAmount(order,
                "This is an ID that the client came up with to describe this order", "50750.272151");
        StarkSigner starkSigner = new StarkSigner();
        Signature signature =
                starkSigner.sign(orderWithClientID, NetworkId.ROPSTEN, new BigInteger(privateKey, 16));
        assertThat(signature.toString(), is(equalTo(mockSignature)));
    }

}
