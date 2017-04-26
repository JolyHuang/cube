package com.sharingif.cube.security.binary;

/**
 * 进制编码器
 * 2016年1月30日 下午8:58:54
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface BinaryCoder {
	
	String getCharset();
	
	void setCharset(String charset);
	
	String encode(byte[] bytes);
	
	byte[] decode(String value);
	
}
