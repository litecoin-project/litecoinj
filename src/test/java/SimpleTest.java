import org.bitcoinj.core.Utils;
import org.junit.Ignore;
import org.junit.Test;
import org.litecoinj.integrations.LitecoinNetParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * Created by over on 11.12.14.
 */
@Ignore
public class SimpleTest {
    private static Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    @Test
    public void genesisHashEqual(){
        new LitecoinNetParameters().getGenesisBlock().verify();
    }

    @Test
    public void testMaxTarget(){
        long maxTarget = Utils.encodeCompactBits(new BigInteger(1, Utils.HEX.decode("00000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".toLowerCase())));
        logger.debug(String.format("%h", maxTarget));

        long bmaxTarget = Utils.encodeCompactBits(new BigInteger(1, Utils.HEX.decode("00000000FFFF0000000000000000000000000000000000000000000000000000".toLowerCase())));
        logger.debug(String.format("bitcoin: %h", bmaxTarget));

        logger.debug(Utils.HEX.encode(Utils.decodeCompactBits(0x1e0fffffL).toByteArray()));
        logger.debug(Utils.HEX.encode(Utils.decodeCompactBits(0x1d00ffffL).toByteArray()));
        logger.debug("{}", String.format("%h", 487264653L));
        logger.debug("{}", String.format("%d", 0x1e0ffff0L));
    }
}
