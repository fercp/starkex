package com.fersoft.signature;

import com.fersoft.converters.StarkwareOrderConverter;
import com.fersoft.exception.FieldExceedMaxException;
import com.fersoft.exception.HashingException;
import com.fersoft.exception.QuantumSizeException;
import com.fersoft.exception.SignException;
import com.fersoft.hashing.ConstantPoints;
import com.fersoft.hashing.PedersonHash;
import com.fersoft.hashing.StarkHashCalculator;
import com.fersoft.types.NetworkId;
import com.fersoft.types.OrderWithClientId;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

/**
 * creates signture from OrderWithClientId privatekey and networkid
 *
 * @author ferat capar
 */
public class StarkSigner {
    private static final StarkHashCalculator STARK_HASH_CALCULATOR = new StarkHashCalculator(new PedersonHash(ConstantPoints.POINTS.get(0)));
    private static final EcSigner EC_SIGNER = new EcSigner(StarkCurve.getInstance());
    private static final StarkwareOrderConverter STARKWARE_ORDER_CONVERTER = new StarkwareOrderConverter();

    public Signature sign(OrderWithClientId order, NetworkId networkId, BigInteger privateKey) throws QuantumSizeException, NoSuchAlgorithmException, FieldExceedMaxException, HashingException, SignException {
        return EC_SIGNER.sign(privateKey, STARK_HASH_CALCULATOR.calculateHash(STARKWARE_ORDER_CONVERTER.fromOrderWithClientId(order, networkId)));
    }
}
