package com.fersoft.signature;

import com.fersoft.exception.SignException;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestEcSigner {
    private static final String EXPECTED_SIGNATURE = "06bca455438f4e337e11dff3897d85fdd425be2eb51bd0db92f34527965934e7003376e6726048f2bb23a6f89e7a7528c8cd832493431dd6c685148517ec752f";
    private final BigInteger PRIVATE_KEY = new BigInteger("07230d8f6fcba9afb8eea3aa67119b5a1bc117500186c384b5aaee85dafbb64c", 16);
    /*
    MESSAGE is Hash representation of sample order:
      {
	humanSize: "1",
	humanPrice: "34.00",
	limitFee: "0.001",
	market: "ATOM-USD",
	side: "BUY",
	expirationIsoTimestamp: "2021-09-20T00:00:00.000Z",
	clientId : "123456",
	positionId: "56277",
    }
     */
    private final BigInteger MESSAGE = new BigInteger("1690222932b6b9f7ec1a92f3950e5332892789e7531336114f588ed08de3a42", 16);

    @Test
    void testSign() throws Exception {
        StarkCurve curve = StarkCurve.getInstance();
        EcSigner signer = new EcSigner(curve);
        Signature signature = signer.sign(PRIVATE_KEY, MESSAGE);
        assertThat(signature.toString(), is(equalTo(EXPECTED_SIGNATURE)));
    }

    @Test
    void testVerifySignature() throws SignException {
        StarkCurve curve = StarkCurve.getInstance();
        EcSigner signer = new EcSigner(curve);
        Signature signature = signer.sign(PRIVATE_KEY, MESSAGE);

        assertTrue(signer.verifySignature(MESSAGE,curve.generatePublicKeyFromPrivateKey(PRIVATE_KEY),signature));
    }
}
