package com.sharingif.cube.security.confidentiality.encrypt.digest;

import com.sharingif.cube.security.binary.BinaryCoder;

/**
 * SHA256加密
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/6/27 下午12:18
 */
public class SHA256Encryptor extends AbstractDigestEncryptor {

    private static final String ALGORITHM = "SHA-256";

    public SHA256Encryptor() {
        super(ALGORITHM);
    }

    public SHA256Encryptor(BinaryCoder binaryCoder){
        super(ALGORITHM, binaryCoder);
    }

}
