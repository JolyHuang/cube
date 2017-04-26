package com.sharingif.cube.components.payments;

/**
 * [支付id生产器]
 * [2015年5月7日 下午8:42:12]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public interface PaymentIdGenerator {
	
	/**
	 * 生成支付流水号
	 * @return
	 */
	String generatePaymentId();

}
