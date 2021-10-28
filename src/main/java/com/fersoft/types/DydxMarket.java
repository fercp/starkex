package com.fersoft.types;

public enum DydxMarket {
    BTC_USD("BTC-USD", DydxAsset.BTC),
    ETH_USD("ETH-USD", DydxAsset.ETH),
    LINK_USD("LINK-USD", DydxAsset.LINK),
    AAVE_USD("AAVE-USD", DydxAsset.AAVE),
    UNI_USD("UNI-USD", DydxAsset.UNI),
    SUSHI_USD("SUSHI-USD", DydxAsset.SUSHI),
    SOL_USD("SOL-USD", DydxAsset.SOL),
    YFI_USD("YFI-USD", DydxAsset.YFI),
    ONEINCH_USD("1INCH-USD", DydxAsset.ONEINCH),
    AVAX_USD("AVAX-USD", DydxAsset.AVAX),
    SNX_USD("SNX-USD", DydxAsset.SNX),
    CRV_USD("CRV-USD", DydxAsset.CRV),
    UMA_USD("UMA-USD", DydxAsset.UMA),
    DOT_USD("DOT-USD", DydxAsset.DOT),
    DOGE_USD("DOGE-USD", DydxAsset.DOGE),
    MATIC_USD("MATIC-USD", DydxAsset.MATIC),
    MKR_USD("MKR-USD", DydxAsset.MKR),
    FIL_USD("FIL-USD", DydxAsset.FIL),
    ADA_USD("ADA-USD", DydxAsset.ADA),
    ATOM_USD("ATOM-USD", DydxAsset.ATOM),
    COMP_USD("COMP-USD", DydxAsset.COMP),
    BCH_USD("BCH-USD", DydxAsset.BCH),
    LTC_USD("LTC-USD", DydxAsset.LTC),
    EOS_USD("EOS-USD", DydxAsset.EOS),
    ALGO_USD("ALGO-USD", DydxAsset.ALGO),
    ZRX_USD("ZRX-USD", DydxAsset.ZRX),
    XMR_USD("XMR-USD", DydxAsset.XMR),
    ZEC_USD("ZEC-USD", DydxAsset.ZEC);


    private final DydxAsset asset;
    private final String value;

    DydxMarket(String value, DydxAsset asset) {
        this.value = value;
        this.asset = asset;
    }

    @Override
    public String toString() {
        return value;
    }

    public DydxAsset getAsset() {
        return asset;
    }
}

