package com.sharingif.cube.security.confidentiality.encrypt;

import java.io.UnsupportedEncodingException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.util.Charset;


/**
 *  
 * @Description:  [BCrypt加密]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年2月24日 下午4:26:00]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年2月24日 下午4:26:00]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class BCryptTextEncryptor implements TextEncryptor {

	private String charset = Charset.UTF8.toString();			// 字符编码
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	public BCryptPasswordEncoder getBcryptPasswordEncoder() {
		return bcryptPasswordEncoder;
	}

	public void setBcryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public byte[] encrypt(byte[] bytes) {
		try {
			return encrypt(new String(bytes, charset)).getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new CubeRuntimeException("BCrypt decrypt unsupported encoding error", e);
		}
	}

	public String encrypt(String text) {
		return bcryptPasswordEncoder.encode(text);
	}

	public boolean supportsDecrypt() {
		return false;
	}

	public String decrypt(String encryptedText) {
		return encryptedText;
	}

	@Override
	public boolean matches(String originalText, String encryptedText) {
		return bcryptPasswordEncoder.matches((CharSequence) originalText, encryptedText);
	}

}
