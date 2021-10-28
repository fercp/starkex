package com.fersoft.signature;

import org.junit.jupiter.api.Test;
import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TestStarkCurve {
    private static final BigInteger P = new BigInteger("3618502788666131213697322783095070105623107215331596699973092056135872020481");
    private static final BigInteger A = BigInteger.ONE;
    private static final BigInteger B = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592307816406665");
    private static final BigInteger N = new BigInteger("3618502788666131213697322783095070105526743751716087489154079457884512865583");
    private static final BigInteger H = BigInteger.ONE;
    private final BigInteger Gx=new BigInteger("874739451078007766457464989774322083649278607533249481151382481072868806602");
    private final BigInteger Gy=new BigInteger("152666792071518830868575557812948353041420400780739481342941381225525861407");
    private static final StarkCurve starkCurve = StarkCurve.getInstance();
    private final BigInteger PRIVATE_KEY = new BigInteger("07230d8f6fcba9afb8eea3aa67119b5a1bc117500186c384b5aaee85dafbb64c", 16);
    private final BigInteger PUBLIC_KEY = new BigInteger("201f61ca4d17d16d6ab7afff893ff35a3fa7557197459d59d03c65fabe3392a7d",16);

    @Test
    void testCreation() {
        assertThat(starkCurve.getP(), is(equalTo(P)));
        assertThat(starkCurve.getA(), is(equalTo(A)));
        assertThat(starkCurve.getB(), is(equalTo(B)));
        assertThat(starkCurve.getN(), is(equalTo(N)));
        assertThat(starkCurve.getH(), is(equalTo(H)));
        assertThat(starkCurve.getGx(),is(equalTo(Gx)));
        assertThat(starkCurve.getGy(),is(equalTo(Gy)));
    }

    @Test
    void testPublicKeyFromPrivateKey(){
        assertThat(starkCurve.generatePublicKeyFromPrivateKey(PRIVATE_KEY),is(equalTo(PUBLIC_KEY)));
    }
}
