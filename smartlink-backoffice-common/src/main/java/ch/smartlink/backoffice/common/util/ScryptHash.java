package ch.smartlink.backoffice.common.util;

import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScryptHash {
    private static Logger logger = LoggerFactory.getLogger(ScryptHash.class);
    private static final int N = 32768;// 2^15
    private static final int r = 8;// relative memory cost
    private static final int p = 1;// parallel factor

    public static String encrypt(String data) {
        if (StringUtils.isBlank(data)) {
            throw new IllegalArgumentException("Cannot Encrypt ScryptHash cause of agrument is null");
        }
        String scryptHash = SCryptUtil.scrypt(data, N, r, p);
        logger.info("encrypted data: " + scryptHash);
        return scryptHash;
    }

    public static boolean check(String data, String scryptHash) {
        boolean res = false;
        if (!StringUtils.isBlank(data) && !StringUtils.isBlank(scryptHash)) {
            try {
                res = SCryptUtil.check(data, scryptHash);
            } catch (IllegalArgumentException ex) {
                logger.error("IllegalArgumentException", ex.getMessage());
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(ScryptHash.encrypt("admin"));
        System.out.println(ScryptHash.check(null, "$s0$f0801$xTjBuzXhsDguqHiAr56JAA==$XU/29e+3sR/WqXyzII27pD8frrCSLthdr8QG/n70nyM="));
    }
}
