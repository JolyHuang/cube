package com.sharingif.cube.security.binary;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**
 * Base64编码器
 * 2016年1月30日 下午9:09:27
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class Base64Coder implements BinaryCoder {

	private String charset = "UTF-8";			// 字符编码
	
	@Override
	public String getCharset() {
		return charset;
	}
	
	@Override
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	@Override
	public String encode(byte[] bytes) {
		try {
			return new String(Base64.encodeBase64(bytes), getCharset());
		} catch (UnsupportedEncodingException e) {
			throw new CubeRuntimeException("base64 encrypt error", e);
		}
	}

	@Override
	public byte[] decode(String value) {
		try {
			return Base64.decodeBase64(value.getBytes(getCharset()));
		} catch (UnsupportedEncodingException e) {
			throw new CubeRuntimeException("base64 decrypt error", e);
		}
	}

}
