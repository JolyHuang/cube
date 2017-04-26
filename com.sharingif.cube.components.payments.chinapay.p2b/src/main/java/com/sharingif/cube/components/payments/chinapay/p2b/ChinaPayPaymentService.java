package com.sharingif.cube.components.payments.chinapay.p2b;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.sharingif.cube.components.payments.AbstractPaymentService;
import com.sharingif.cube.components.payments.chinapay.p2b.pay.ChinaPayPay;
import com.sharingif.cube.components.payments.chinapay.p2b.refund.ChinaPayRefund;
import com.sharingif.cube.security.key.ISecretKeyRepertory;

/**
 * [银联支付服务]
 * [2015年5月13日 下午9:32:02]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class ChinaPayPaymentService extends AbstractPaymentService<ChinaPayPay, ChinaPayRefund> {
	
	public ChinaPayPaymentService(String privateKeyCode, String publicKeyCode, ISecretKeyRepertory secretKeyRepertory) {
		
		SecureLink privateSecretLink = new SecureLink((PrivateKey)secretKeyRepertory.getSecretKey(privateKeyCode, Object.class));
		SecureLink publicSecretLink = new SecureLink((PrivateKey)secretKeyRepertory.getSecretKey(publicKeyCode, Object.class));
		
		setPrivateSecret(privateSecretLink);
		setPublicSecret(publicSecretLink);
	}

	@Override
	public String getPaymentType() {
		return ChinaPayPay.PAYMENT_TYPE;
	}

}
