package com.fersoft.signature;

import com.fersoft.exception.FieldExceedMaxException;
import com.fersoft.exception.HashingException;
import com.fersoft.exception.QuantumSizeException;
import com.fersoft.exception.SignException;
import com.fersoft.types.DydxMarket;
import com.fersoft.types.NetworkId;
import com.fersoft.types.Order;
import com.fersoft.types.OrderWithClientIdAndQuoteAmount;
import com.fersoft.types.StarkwareOrderSide;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TestStarkSigner {
    private static final String EXPECTED_SIGNATURE = "06bca455438f4e337e11dff3897d85fdd425be2eb51bd0db92f34527965934e7003376e6726048f2bb23a6f89e7a7528c8cd832493431dd6c685148517ec752f";
    private final BigInteger PRIVATE_KEY = new BigInteger("07230d8f6fcba9afb8eea3aa67119b5a1bc117500186c384b5aaee85dafbb64c", 16);

    @Test
    void testSign() throws QuantumSizeException, NoSuchAlgorithmException, FieldExceedMaxException, HashingException, SignException {
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
}
