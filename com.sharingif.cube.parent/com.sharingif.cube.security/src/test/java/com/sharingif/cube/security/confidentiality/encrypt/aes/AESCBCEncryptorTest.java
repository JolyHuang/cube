package com.sharingif.cube.security.confidentiality.encrypt.aes;

import org.junit.Test;

import com.sharingif.cube.security.binary.Base64Coder;
import com.sharingif.cube.security.binary.HexCoder;

/**
 * AESCBCEncryptorTest
 * 2016年1月29日 下午8:49:03
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class AESCBCEncryptorTest {
	
	@Test
	public void testAESCBCHexEncryptor(){
		AESCBCEncryptor encryptor = new AESCBCEncryptor(new HexCoder());
		String str = encryptor.encrypt("1111111112300000000000000000000000000000000");
		System.out.println(str.toUpperCase());
		System.out.println(encryptor.decrypt(str));
		
		String str2 = encryptor.encrypt("1121111112300000000000000000000000000000000");
		System.out.println(str2.toUpperCase());
		System.out.println(encryptor.decrypt(str2));
		
		String str3 = encryptor.encrypt("1131111112300000000000000000000000000000000");
		System.out.println(str3.toUpperCase());
		System.out.println(encryptor.decrypt(str3));
	}
	
	@Test
	public void testAESCBCBase64Encryptor(){
		AESCBCEncryptor encryptor = new AESCBCEncryptor(new Base64Coder());
		String str = encryptor.encrypt("1111111112300000000000000000000000000000000");
		System.out.println(str.toUpperCase());
		System.out.println(encryptor.decrypt(str));
		
		String str2 = encryptor.encrypt("1121111112300000000000000000000000000000000");
		System.out.println(str2.toUpperCase());
		System.out.println(encryptor.decrypt(str2));
		
		String str3 = encryptor.encrypt("1131111112300000000000000000000000000000000");
		System.out.println(str3.toUpperCase());
		System.out.println(encryptor.decrypt(str3));
	}

}
