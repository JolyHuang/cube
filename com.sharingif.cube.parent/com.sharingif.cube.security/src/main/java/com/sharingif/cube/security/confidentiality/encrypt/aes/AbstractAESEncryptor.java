package com.sharingif.cube.security.confidentiality.encrypt.aes;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.util.Charset;
import com.sharingif.cube.security.binary.BinaryCoder;
import com.sharingif.cube.security.binary.HexCoder;

/**
 * [AES对称加密]
 * [2015年4月7日 下午11:17:34]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public abstract class AbstractAESEncryptor {
	
	//密钥算法
    private static final String KEY_ALGORITHM = "AES";
    
  	private int keySize = 128;									// AES要求密钥长度为128,192,256位
    private String charset = Charset.UTF8.toString();			// 字符编码
    
	private Cipher encryptCipher;
	private Cipher decryptCipher;
	
	private String cipherAlgorithm;
	private BinaryCoder binaryCoder;
	
	
	public AbstractAESEncryptor(String cipherAlgorithm) {
		
		this.cipherAlgorithm = cipherAlgorithm;
		
		binaryCoder = new HexCoder();
		SecretKey secretKey = createSecretKey();
		Key key = createKey(secretKey);
		IvParameterSpec ivParameterSpec = createIvParameterSpec(secretKey);
		
		encryptCipher = getEncryptCipher(key, ivParameterSpec);
		decryptCipher = getDecryptCipher(key, ivParameterSpec);
	}
	
	public AbstractAESEncryptor(String cipherAlgorithm, BinaryCoder binaryCoder){
		this(cipherAlgorithm);
		this.binaryCoder = binaryCoder;
		this.binaryCoder.setCharset(charset);
	}
	
	public Cipher getEncryptCipher() {
		return encryptCipher;
	}

	public Cipher getDecryptCipher() {
		return decryptCipher;
	}
	
	public BinaryCoder getBinaryCoder() {
		return binaryCoder;
	}

	protected SecretKey createSecretKey() {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new CubeRuntimeException("generator key error", e);
		}
		keyGenerator.init(keySize);														// 初始化密钥生成器:AES要求密钥长度为128,192,256位
        return keyGenerator.generateKey();												// 生成密钥
	}
	
	protected Key createKey(SecretKey secretKey) {
        byte[] keyByte = secretKey.getEncoded();										// 获取二进制密钥编码形式
        return new SecretKeySpec(keyByte, KEY_ALGORITHM);								// 转换密钥
	}
	
	protected IvParameterSpec createIvParameterSpec(SecretKey secretKey) {
		return new IvParameterSpec(secretKey.getEncoded());
	}
	
	protected Cipher getEncryptCipher(Key key, AlgorithmParameterSpec algorithmParameterSpec) {
		try {
			Cipher encryptCipher = Cipher.getInstance(cipherAlgorithm);					// 实例化Cipher对象，它用于完成实际的加密操作
			encryptCipher.init(Cipher.ENCRYPT_MODE, key, algorithmParameterSpec);		// 初始化Cipher对象，设置为加密模式
			return encryptCipher;
		} catch (Exception e) {
			throw new CubeRuntimeException("generator encrypt cipher error", e);
		}
		
	}
	
	protected Cipher getDecryptCipher(Key key, AlgorithmParameterSpec algorithmParameterSpec) {
		try {
			Cipher decryptCipher = Cipher.getInstance(cipherAlgorithm);					// 初始化Cipher对象，设置为解密模式 
			decryptCipher.init(Cipher.DECRYPT_MODE, key, algorithmParameterSpec);		// 执行解密操作
			return decryptCipher;
		} catch (Exception e) {
			throw new CubeRuntimeException("generator decrypt cipher error", e);
		}
		
	}
	
	public byte[] encryptAES(String text) {
		try {
			return encryptCipher.doFinal(text.getBytes(getCharset()));
		} catch (CubeRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new CubeRuntimeException("aes encrypt error", e);
		}
	}

	public byte[] decryptAES(String encryptedText) {
		try {
			return decryptCipher.doFinal(binaryCoder.decode(encryptedText));
		} catch (CubeRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new CubeRuntimeException("aes decrypt error", e);
		}
	}

	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}
