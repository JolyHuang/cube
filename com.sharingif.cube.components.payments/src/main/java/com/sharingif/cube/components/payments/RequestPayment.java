package com.sharingif.cube.components.payments;

/**
 * [支付请求数据]
 * [2015年6月27日 下午6:53:40]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class RequestPayment {
	
	private Payment payment;
	private String requestView;
	
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public String getRequestView() {
		return requestView;
	}
	public void setRequestView(String requestView) {
		this.requestView = requestView;
	}

}
