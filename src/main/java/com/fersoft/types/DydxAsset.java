package com.fersoft.types;

import java.math.BigInteger;

public enum DydxAsset {
    USDC("USDC", new BigInteger("0",16),6),
    BTC("BTC", new BigInteger("4254432d3130000000000000000000",16),10),
    ETH("ETH", new BigInteger("4554482d3900000000000000000000",16),9),
    LINK("LINK", new BigInteger("4c494e4b2d37000000000000000000",16),7),
    AAVE("AAVE", new BigInteger("414156452d38000000000000000000",16),8),
    UNI("UNI", new BigInteger("554e492d3700000000000000000000",16),7),
    SUSHI("SUSHI", new BigInteger("53555348492d370000000000000000",16),7),
    SOL("SOL", new BigInteger("534f4c2d3700000000000000000000",16),7),
    YFI("YFI", new BigInteger("5946492d3130000000000000000000",16),10),
    ONEINCH("1INCH", new BigInteger("31494e43482d370000000000000000",16),7),
    AVAX("AVAX", new BigInteger("415641582d37000000000000000000",16),7),
    SNX("SNX", new BigInteger("534e582d3700000000000000000000",16),7),
    CRV("CRV", new BigInteger("4352562d3600000000000000000000",16),6),
    UMA("UMA", new BigInteger("554d412d3700000000000000000000",16),7),
    DOT("DOT", new BigInteger("444f542d3700000000000000000000",16),7),
    DOGE("DOGE", new BigInteger("444f47452d35000000000000000000",16),5),
    MATIC("MATIC", new BigInteger("4d415449432d360000000000000000",16),6),
    MKR("MKR", new BigInteger("4d4b522d3900000000000000000000",16),9),
    FIL("FIL", new BigInteger("46494c2d3700000000000000000000",16),7),
    ADA("ADA", new BigInteger("4144412d3600000000000000000000",16),6),
    ATOM("ATOM", new BigInteger("41544f4d2d37000000000000000000",16),7),
    COMP("COMP", new BigInteger("434f4d502d38000000000000000000",16),8),
    BCH("BCH", new BigInteger("4243482d3800000000000000000000",16),8),
    LTC("LTC", new BigInteger("4c54432d3800000000000000000000",16),8),
    EOS("EOS", new BigInteger("454f532d3600000000000000000000",16),6),
    ALGO("ALGO", new BigInteger("414c474f2d36000000000000000000",16),6),
    ZRX("ZRX", new BigInteger("5a52582d3600000000000000000000",16),6),
    XMR("XMR", new BigInteger("584d522d3800000000000000000000",16),8),
    ZEC("ZEC", new BigInteger("5a45432d3800000000000000000000",16),8);

    private final String value;
    private final BigInteger assetId;
    private final int resolution;

    DydxAsset(String value, BigInteger assetId, int resolution) {
        this.value = value;
        this.assetId = assetId;
        this.resolution = resolution;
    }

    public String getValue() {
        return value;
    }

    public BigInteger getAssetId() {
        return assetId;
    }

    public int getResolution() {
        return resolution;
    }
}
