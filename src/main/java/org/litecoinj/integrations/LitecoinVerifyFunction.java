package org.litecoinj.integrations;

import com.lambdaworks.crypto.SCrypt;
import org.bitcoinj.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;

/**
 * Created by over on 11.12.14.
 */
public class LitecoinVerifyFunction implements VerifyFunction {
    private static Logger logger = LoggerFactory.getLogger(LitecoinVerifyFunction.class);

    @Override
    public boolean verify(Block block, boolean throwException) throws VerificationException {
        BigInteger target = block.getDifficultyTargetAsInteger();
        byte[] bytes;

        try (ByteArrayOutputStream bos = new UnsafeByteArrayOutputStream(Block.HEADER_SIZE)) {
            block.writeHeader(bos);
            bytes = bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e); // Cannot happen.
        }

        if (bytes.length != 80)
            if (throwException)
                throw new VerificationException("Incorrect block header size!");
            else
                return false;

        byte[] hashed;
        try {
            hashed = Utils.reverseBytes(SCrypt.scryptJ(bytes, bytes, 1024, 1, 1, 32));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e); //Cannot happen
        }

        if (new BigInteger(1, hashed).compareTo(target) == 1)
            if (throwException)
                throw new VerificationException("Hash is higher than target: " + Utils.HEX.encode(hashed) + " vs "
                        + target.toString(16));
            else
                return false;

        return true;
    }
}
