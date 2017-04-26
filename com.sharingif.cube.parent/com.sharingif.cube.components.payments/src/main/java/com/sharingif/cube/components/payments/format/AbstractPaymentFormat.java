package com.sharingif.cube.components.payments.format;

import com.sharingif.cube.components.payments.Payment;

/**
 * [抽象的支付数据格式化，提供各种格式化类型值]
 * [2015年5月8日 下午11:59:33]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public abstract class AbstractPaymentFormat {
	
	/**
     * 交易请求数据类型(文本)
     */	
	public static final String FORMATTYPE_TEXT = "0";
	/**
     * 交易请求数据类型(json)
     */	
	public static final String FORMATTYPE_JSON = "1";
	/**
     * 交易请求数据类型(xml)
     */	
	public static final String FORMATTYPE_XML = "2";
	
	private String formatType;
	
	public void useText() {
		formatType = FORMATTYPE_TEXT;
	}

	public void useJson() {
		formatType = FORMATTYPE_JSON;
	}

	public void useXml() {
		formatType = FORMATTYPE_XML;
	}

	public final String getFormatType() {
		return formatType;
	}
	
	/**
	 * 格式化支付数据
	 * @param payment : Payment
	 * @return
	 */
	abstract public String formatPayment(Payment payment);

}
