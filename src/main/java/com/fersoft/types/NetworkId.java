package com.fersoft.types;

import java.math.BigInteger;

public enum NetworkId {
    MAINNET(1, "a0b86991c6218b36c1d19d4a2e9eb0ce3606eb48",new BigInteger("02893294412a4c8f915f75892b395ebbf6859ec246ec365c3b1f56f47c3a0a5d",16)),
    ROPSTEN(3, "8707a5bf4c2842d46b31a405ba41b858c0f876c4",new BigInteger("02c04d8b650f44092278a7cb1e1028c82025dff622db96c934b611b84cc8de5a",16));

    private final int id;
    private final String assetAddress;
    private final BigInteger collateralAddressId;

    NetworkId(int id, String assetAddress,BigInteger collateralAddressId) {
        this.id = id;
        this.assetAddress = assetAddress;
        this.collateralAddressId = collateralAddressId;
    }

    public int getId() {
        return this.id;
    }

    public String getAssetAddress() {
        return assetAddress;
    }

    public BigInteger getCollateralAddressId() {
        return collateralAddressId;
    }
}
