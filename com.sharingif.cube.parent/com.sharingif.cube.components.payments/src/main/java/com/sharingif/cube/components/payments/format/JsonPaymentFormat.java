package com.sharingif.cube.components.payments.format;

import com.sharingif.cube.components.json.IJsonService;
import com.sharingif.cube.components.payments.Payment;

/**
 * [json支付数据格式化]
 * [2015年5月12日 下午8:47:21]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class JsonPaymentFormat extends AbstractPaymentFormat {
	
	
	public JsonPaymentFormat() {
		super.useJson();
	}

	@Override
	public String formatPayment(Payment payment) {
		return getJsonService().objectoJson(payment);
	}

	private IJsonService jsonService;

	public IJsonService getJsonService() {
		return jsonService;
	}

	public void setJsonService(IJsonService jsonService) {
		this.jsonService = jsonService;
	}
	

}
