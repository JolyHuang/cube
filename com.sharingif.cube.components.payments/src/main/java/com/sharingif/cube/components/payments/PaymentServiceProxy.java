package com.sharingif.cube.components.payments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**
 * [支付代理]
 * [2015年4月26日 下午9:24:26]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class PaymentServiceProxy<P extends Payment, R extends Payment> implements IPaymentService<P, R> {

	private Map<String, IPaymentService<P, R>> paymentServiceMap;

	public void setPaymentServiceList(List<IPaymentService<P, R>> paymentServiceList) {
		paymentServiceMap = new HashMap<String, IPaymentService<P, R>>(paymentServiceList.size());
		for(IPaymentService<P, R> paymentService : paymentServiceList){
			paymentServiceMap.put(paymentService.getPaymentType(), paymentService);
		}
	}
	
	@Override
	public boolean support(Payment payment) {
		return true;
	}
	
	@Override
	public String getPaymentType() {
		return "ALL";
	}
	
	@Override
	public RequestPayment handlePay(Payment payRequest) {
		IPaymentService<P, R> paymentService = paymentServiceMap.get(payRequest.getPaymentType());
		
		if(paymentService == null)
			throw new CubeRuntimeException("pay type is wrong");
		
		return paymentService.handlePay(payRequest);
	}
	@Override
	public ResponsePayment callBackPay(Map<String,Object> payResponse) {
		IPaymentService<P, R> paymentService = paymentServiceMap.get(payResponse.get(Payment.PAYMENT_TYPE));
		payResponse.remove(Payment.PAYMENT_TYPE);
		
		if(paymentService == null)
			throw new CubeRuntimeException("callbackPay type is wrong");
		
		return paymentService.callBackPay(payResponse);
	}
	@Override
	public RequestPayment handleRefund(R refundRequest) {
		IPaymentService<P, R> paymentService = paymentServiceMap.get(refundRequest.getPaymentType());
		
		if(null == paymentService)
			throw new CubeRuntimeException("refund type is wrong");
		
		return paymentService.handleRefund(refundRequest);
	}
	@Override
	public ResponsePayment callBackRefund(Map<String,Object> refundResponse) {
		IPaymentService<P, R> paymentService = paymentServiceMap.get(refundResponse.get(Payment.PAYMENT_TYPE));
		refundResponse.remove(Payment.PAYMENT_TYPE);
		
		if(null == paymentService)
			throw new CubeRuntimeException("callbackRefund type is wrong");
		
		return paymentService.callBackRefund(refundResponse);
	}

	@Override
	public String getPayView() {
		return null;
	}

	@Override
	public String getRefundView() {
		return null;
	}
	
	public String getPayView(Payment payment) {
		IPaymentService<P, R> paymentService = paymentServiceMap.get(payment.getPaymentType());
		return paymentService.getPayView();
	}

	public String getRefundView(Payment payment) {
		IPaymentService<P, R> paymentService = paymentServiceMap.get(payment.getPaymentType());
		return paymentService.getRefundView();
	}

}
