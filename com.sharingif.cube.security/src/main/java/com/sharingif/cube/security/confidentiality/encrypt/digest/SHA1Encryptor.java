package com.sharingif.cube.security.confidentiality.encrypt.digest;

import com.sharingif.cube.security.binary.BinaryCoder;

/**
 * SHA1加密
 * 2016年3月27日 下午4:16:39
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SHA1Encryptor extends AbstractDigestEncryptor {
	
	private static final String ALGORITHM = "SHA-1";
	
	public SHA1Encryptor() {
		super(ALGORITHM);
	}
	
	public SHA1Encryptor(BinaryCoder binaryCoder){
		super(ALGORITHM, binaryCoder);
	}
	
}
