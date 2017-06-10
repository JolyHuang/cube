package com.sharingif.cube.security.confidentiality.encrypt.md5;

import org.junit.Test;

import com.sharingif.cube.security.confidentiality.encrypt.digest.MD5Encryptor;

/**
 * MD5EncryptorTest
 * 2016年1月30日 下午7:55:18
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MD5EncryptorTest {
	
	@Test
	public void testMD5HexEncryptor() {
		MD5Encryptor encryptor = new MD5Encryptor();
		System.out.println(encryptor.encrypt("3333"));
	}
	
	@Test
	public void testMD5Base64Encryptor() {
	}

}
