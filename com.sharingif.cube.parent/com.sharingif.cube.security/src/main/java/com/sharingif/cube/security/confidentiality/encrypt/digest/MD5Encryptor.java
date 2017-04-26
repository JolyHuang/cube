package com.sharingif.cube.security.confidentiality.encrypt.digest;

import com.sharingif.cube.security.binary.BinaryCoder;

/**
 * [MD5加密]
 * [2015年6月14日 下午9:59:42]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class MD5Encryptor extends AbstractDigestEncryptor {
	
	private static final String ALGORITHM = "MD5";

	public MD5Encryptor() {
		super(ALGORITHM);
	}
	
	public MD5Encryptor(BinaryCoder binaryCoder){
		super(ALGORITHM, binaryCoder);
	}
	
}
