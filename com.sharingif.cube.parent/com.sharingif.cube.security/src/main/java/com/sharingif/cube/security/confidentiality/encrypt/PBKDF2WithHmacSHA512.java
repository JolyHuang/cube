package com.sharingif.cube.security.confidentiality.encrypt;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * PBKDF2WithHmacSHA512
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/6/28 下午6:59
 */
public class PBKDF2WithHmacSHA512 {

    private static final SecretKeyFactory SKF;

    static {
        try {
            SKF = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public SecretKey createSecretKey(final char[] content, final byte[] salt, final int iterations, final int keyLength) {
        try {
            PBEKeySpec spec = new PBEKeySpec(content, salt, iterations, keyLength);
            return SKF.generateSecret(spec);
        } catch(InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
