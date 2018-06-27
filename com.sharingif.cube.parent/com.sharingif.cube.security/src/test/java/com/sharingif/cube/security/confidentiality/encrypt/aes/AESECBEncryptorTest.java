package com.sharingif.cube.security.confidentiality.encrypt.aes;

import org.junit.Test;

import com.sharingif.cube.security.binary.Base64Coder;
import com.sharingif.cube.security.binary.HexCoder;

import java.io.UnsupportedEncodingException;

/**
 * AESECBEncryptorTest
 * 2016年1月29日 下午8:48:47
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class AESECBEncryptorTest {
	
	@Test
	public void testAESECBHexEncryptor() throws UnsupportedEncodingException {

		byte[] secretKey = AESCBCEncryptor.generateSecretKey(128);

		System.out.println(secretKey);

		AESECBEncryptor encryptor = new AESECBEncryptor(secretKey, new HexCoder());
		String str = encryptor.encrypt("1111111112300000000000000000000000000000000");
		System.out.println(str);
		System.out.println(encryptor.decrypt(str));

		String str2 = encryptor.encrypt("1121111112300000000000000000000000000000000");
		System.out.println(str2.toUpperCase());
		System.out.println(encryptor.decrypt(str2));

		String str3 = encryptor.encrypt("1131111112300000000000000000000000000000000");
		System.out.println(str3.toUpperCase());
		System.out.println(encryptor.decrypt(str3));
	}
	
	@Test
	public void testAESECBBase64Encryptor() throws UnsupportedEncodingException {
		byte[] secretKey = AESCBCEncryptor.generateSecretKey(256);

		Base64Coder base64Coder = new Base64Coder();

		System.out.println(base64Coder.encode(secretKey));

		System.out.println(secretKey);

		AESECBEncryptor encryptor = new AESECBEncryptor(secretKey, new Base64Coder());
		String str = encryptor.encrypt("1111111112300000000000000000000000000000000");
		System.out.println(str);
		System.out.println(encryptor.decrypt(str));
		
		String str2 = encryptor.encrypt("1121111112300000000000000000000000000000000");
		System.out.println(str2.toUpperCase());
		System.out.println(encryptor.decrypt(str2));
		
		String str3 = encryptor.encrypt("1131111112300000000000000000000000000000000");
		System.out.println(str3.toUpperCase());
		System.out.println(encryptor.decrypt(str3));
	}

	@Test
	public void test() throws UnsupportedEncodingException {
		Base64Coder base64Coder = new Base64Coder();
		byte[] keysByte = base64Coder.decode("5+5u1xAgI9VnoO5R/8rU7oirbiudRggkL2e8bPgu1g0=");
		AESECBEncryptor encryptor = new AESECBEncryptor(keysByte, base64Coder);
		System.out.println(encryptor.encrypt("r3[vs5Utmk6Iceg6"));
		System.out.println(encryptor.decrypt("QlroHFybYnzi0gz1zOPY0fl4tduFyfcV7P5fJMl8FOU="));
	}

}
