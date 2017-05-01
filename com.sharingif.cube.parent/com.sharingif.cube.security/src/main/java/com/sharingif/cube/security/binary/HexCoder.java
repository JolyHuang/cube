package com.sharingif.cube.security.binary;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.util.Charset;

/**
 * 十六进制编码器
 * 2016年1月30日 下午9:03:06
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HexCoder implements BinaryCoder {

	private String charset = Charset.UTF8.toString();			// 字符编码
	
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
		return new String(Hex.encodeHex(bytes));
	}

	@Override
	public byte[] decode(String value) {
		try {
			return Hex.decodeHex(value.toCharArray());
		} catch (DecoderException e) {
			throw new CubeRuntimeException("hex decode error", e);
		}
	}

}
