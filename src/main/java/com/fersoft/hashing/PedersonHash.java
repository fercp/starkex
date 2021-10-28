package com.fersoft.hashing;

import com.fersoft.exception.PedersonHashException;
import com.fersoft.exception.PedersonHashInputException;
import org.bouncycastle.math.ec.ECPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Pederson hash function
 *
 * @author ferat capar
 * @link https://docs.starkware.co/starkex-v3/crypto/pedersen-hash-function
 */
public class PedersonHash implements HashFunction {
    private static final Logger logger = LoggerFactory.getLogger(PedersonHash.class);
    private static final BigInteger PRIME = new BigInteger("800000000000011000000000000000000000000000000000000000000000001", 16);
    private static final Map<BigInteger, Map<BigInteger, BigInteger>> CACHE = new ConcurrentHashMap<>();
    private final ECPoint basePoint;

    public PedersonHash(ECPoint basePoint) {
        this.basePoint = basePoint;
    }

    @Override
    public BigInteger createHash(BigInteger left, BigInteger right) throws PedersonHashException, PedersonHashInputException {
        ECPoint point = calculateWith(basePoint, 0, left);
        point = calculateWith(point, 1, right);
        logger.trace("computed hash for left:{} and right{} , x : {} y : {} ", left, right, point.getXCoord().toBigInteger(), point.getYCoord().toBigInteger());
        return point.getXCoord().toBigInteger();
    }

    private ECPoint calculateWith(ECPoint point, int index, BigInteger field) throws PedersonHashInputException, PedersonHashException {
        ECPoint newPoint = point;
        checkField(field);
        for (int i = 0; i < 252; i++) {
            ECPoint pt = ConstantPoints.POINTS.get(2 + index * 252 + i);

            if (point.getXCoord().equals(pt.getXCoord())) {
                logger.error("Error computing pedersen hash");
                throw new PedersonHashException();
            }
            if (!field.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
                newPoint = newPoint.add(pt);
            }
            field = field.shiftRight(1);
        }
        return newPoint;
    }

    private void checkField(BigInteger field) throws PedersonHashInputException {
        if (!(field.compareTo(BigInteger.ZERO) >= 0 && field.compareTo(PRIME) < 0)) {
            logger.error("Input to pedersen hash out of range: {}", field);
            throw new PedersonHashInputException(field.toString(16));
        }
    }

    @Override
    public BigInteger hashFromCache(BigInteger left, BigInteger right) throws PedersonHashException, PedersonHashInputException {
        Map<BigInteger, BigInteger> innerCache = CACHE.computeIfAbsent(left, k -> new ConcurrentHashMap<>());
        if (!innerCache.containsKey(right)) {
            innerCache.put(right, createHash(left, right));
        }
        return innerCache.get(right);
    }
}
