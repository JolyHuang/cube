package com.sharingif.cube.security.confidentiality.encrypt.digest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.util.Charset;
import com.sharingif.cube.security.binary.BinaryCoder;
import com.sharingif.cube.security.binary.HexCoder;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;

/**
 * [摘要]
 * [2015年6月14日 下午9:59:42]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public abstract class AbstractDigestEncryptor implements TextEncryptor {
	
	private String charset = Charset.UTF8.toString();				// 字符编码
	private String salt;											// 盐值
	
	private MessageDigest messageDigest;
	private BinaryCoder binaryCoder;
	
	public AbstractDigestEncryptor(String algorithm){
		binaryCoder = new HexCoder();
		try {
			messageDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new CubeRuntimeException("generator MessageDigest error", e);
		}
	}

	public AbstractDigestEncryptor(String algorithm, BinaryCoder binaryCoder){
		this(algorithm);
		this.binaryCoder = binaryCoder;
		this.binaryCoder.setCharset(charset);
	}

	@Override
	public byte[] encrypt(byte[] bytes) {
		return messageDigest.digest(bytes);
	}

	@Override
	public String encrypt(String text) {
		byte[] bytes;
		try {
			bytes = encrypt(text.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			throw new CubeRuntimeException("digest error", e);
		}
        return binaryCoder.encode(bytes); 
	}
	
	@Override
	public boolean supportsDecrypt() {
		return false;
	}

	@Override
	public String decrypt(String encryptedText) {
		return encryptedText;
	}
	
	@Override
	public boolean matches(String originalText, String encryptedText) {
		return (originalText).equals(encryptedText);
	}
	
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
