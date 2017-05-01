package com.sharingif.cube.core.util;

/**
 * 字符集枚举
 * 2017年5月1日 下午10:07:29
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public enum Charset {
	
	UTF8("UTF-8");
	
	private String value;
	Charset(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return this.value;
	}
	
	

}
