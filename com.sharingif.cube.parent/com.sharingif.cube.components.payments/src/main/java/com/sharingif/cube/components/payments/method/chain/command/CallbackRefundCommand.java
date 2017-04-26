package com.sharingif.cube.components.payments.method.chain.command;

import java.util.Map;

import com.sharingif.cube.components.payments.Payment;
import com.sharingif.cube.components.payments.PaymentServiceProxy;
import com.sharingif.cube.components.payments.ResponsePayment;

/**
 *
 * @Description:  [退款回调命令]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月8日 下午6:18:12]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月8日 下午6:18:12]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class CallbackRefundCommand extends CallbackPayCommand {
	
	private PaymentServiceProxy<Payment, Payment> paymentServiceProxy;

	public PaymentServiceProxy<Payment, Payment> getPaymentServiceProxy() {
		return paymentServiceProxy;
	}
	public void setPaymentServiceProxy(PaymentServiceProxy<Payment, Payment> paymentServiceProxy) {
		this.paymentServiceProxy = paymentServiceProxy;
	}
	@Override
	protected ResponsePayment getResponsePayment(Map<String, Object> responseMap) {
		return paymentServiceProxy.callBackRefund(responseMap);
	}

	

}
