package com.sharingif.cube.security.confidentiality.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HmacSHA512
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/6/29 下午2:23
 */
public class HmacSHA512 {

    private static final String HMAC_SHA512 = "HmacSHA512";

    private Mac mac;

    public HmacSHA512(byte[] key) {
        try {
            mac = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(key, HMAC_SHA512);
            mac.init(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] doFinal(byte[] value) {
        return mac.doFinal(value);
    }

}
