package com.fersoft.hashing;

import com.fersoft.exception.FieldExceedMaxException;
import com.fersoft.exception.HashingException;
import java.math.BigInteger;

/**
 * Base class for hash calculators
 *
 * @param <T>
 * @author ferat capar
 */
public interface HashCalculator<T> {
    BigInteger calculateHash(T message) throws FieldExceedMaxException, HashingException;
}
