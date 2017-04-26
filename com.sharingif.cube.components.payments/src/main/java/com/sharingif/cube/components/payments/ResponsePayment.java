package com.sharingif.cube.components.payments;



/**
 * [支付响应数据]
 * [2015年6月25日 下午11:45:06]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class ResponsePayment {
	
	private String paymentId;
	private boolean payStatus;
	private Payment payment;
	private String orderNo;
	private String responseView;
	
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public boolean getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(boolean payStatus) {
		this.payStatus = payStatus;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getResponseView() {
		return responseView;
	}
	public void setResponseView(String responseView) {
		this.responseView = responseView;
	}
	
	
}
