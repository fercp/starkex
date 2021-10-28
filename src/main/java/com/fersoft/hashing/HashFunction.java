package com.fersoft.hashing;

import com.fersoft.exception.HashingException;
import java.math.BigInteger;

/**
 * Hash function interface
 *
 * @author ferat capar
 */
public interface HashFunction {
    /**
     * creates hash from the parameters
     *
     * @param left
     * @param right
     * @return hash number
     * @throws HashingException
     */
    BigInteger createHash(BigInteger left,BigInteger right) throws HashingException;

    /**
     * first look up cache for the hash value if not exists there calculates hash and put into cache
     *
     *
     * @param left
     * @param right
     * @return hash number
     * @throws HashingException
     */
    BigInteger hashFromCache(BigInteger left,BigInteger right) throws HashingException;
}
