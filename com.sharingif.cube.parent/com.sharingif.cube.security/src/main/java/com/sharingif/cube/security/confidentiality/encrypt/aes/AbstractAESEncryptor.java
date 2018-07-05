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
    
    private String charset = Charset.UTF8.toString();			// 字符编码
    
	private Cipher encryptCipher;
	private Cipher decryptCipher;
	
	private String cipherAlgorithm;
	private BinaryCoder binaryCoder;
	
	
	public AbstractAESEncryptor(String cipherAlgorithm, byte[] secretKey) {
		this(cipherAlgorithm, secretKey, secretKey, new HexCoder());
	}
	
	public AbstractAESEncryptor(String cipherAlgorithm, byte[] secretKey, byte[]  ivParameterKey, BinaryCoder binaryCoder) {
		this.cipherAlgorithm = cipherAlgorithm;

		Key key = createKey(secretKey);

		IvParameterSpec ivParameterSpec = createIvParameterSpec(ivParameterKey);
		encryptCipher = createEncryptCipher(key, ivParameterSpec);
		decryptCipher = createDecryptCipher(key, ivParameterSpec);

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

	protected Key createKey(byte[] secretKey) {
        return new SecretKeySpec(secretKey, KEY_ALGORITHM);								// 转换密钥
	}

	protected IvParameterSpec createIvParameterSpec(byte[] key) {
		return new IvParameterSpec(key);
	}
	
	protected Cipher createEncryptCipher(Key key, AlgorithmParameterSpec algorithmParameterSpec) {
		try {
			Cipher encryptCipher = Cipher.getInstance(cipherAlgorithm);					// 实例化Cipher对象，它用于完成实际的加密操作
			encryptCipher.init(Cipher.ENCRYPT_MODE, key, algorithmParameterSpec);		// 初始化Cipher对象，设置为加密模式
			return encryptCipher;
		} catch (Exception e) {
			throw new CubeRuntimeException("generator encrypt cipher error", e);
		}
		
	}
	
	protected Cipher createDecryptCipher(Key key, AlgorithmParameterSpec algorithmParameterSpec) {
		try {
			Cipher decryptCipher = Cipher.getInstance(cipherAlgorithm);					// 初始化Cipher对象，设置为解密模式 
			decryptCipher.init(Cipher.DECRYPT_MODE, key, algorithmParameterSpec);		// 执行解密操作
			return decryptCipher;
		} catch (Exception e) {
			throw new CubeRuntimeException("generator decrypt cipher error", e);
		}
		
	}

	protected byte[] encryptAES(byte[] bytes) {
		try {
			return encryptCipher.doFinal(bytes);
		} catch (CubeRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new CubeRuntimeException("aes encrypt error", e);
		}
	}

	protected byte[] encryptAES(String text) {
		try {
			return encryptCipher.doFinal(text.getBytes(getCharset()));
		} catch (CubeRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new CubeRuntimeException("aes encrypt error", e);
		}
	}

	protected byte[] decryptAES(String encryptedText) {
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


	public static byte[] generateSecretKey(int keySize) {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new CubeRuntimeException("generator key error", e);
		}
		keyGenerator.init(keySize);														// 初始化密钥生成器:AES要求密钥长度为128,192,256位
		SecretKey secretKey = keyGenerator.generateKey();								// 生成密钥

		return secretKey.getEncoded();
	}

}
