package com.sharingif.cube.components.payments.handler.chain.command;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.components.payments.Payment;
import com.sharingif.cube.components.payments.PaymentServiceProxy;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.web.handler.chain.command.AbstractWebHandlerMethodCommand;

/**
 *
 * @Description:  [退款命令]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月8日 下午12:59:00]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月8日 下午12:59:00]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class RefundCommand extends AbstractWebHandlerMethodCommand {
	private static final String PAYMENTS_KEY = "_payment";
	
	private PaymentServiceProxy<Payment, Payment> paymentServiceProxy;

	public PaymentServiceProxy<Payment, Payment> getPaymentServiceProxy() {
		return paymentServiceProxy;
	}
	public void setPaymentServiceProxy(PaymentServiceProxy<Payment, Payment> paymentServiceProxy) {
		this.paymentServiceProxy = paymentServiceProxy;
	}
	
	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		Payment refundRequest = content.getObject(Payment.class);
		
		paymentServiceProxy.handleRefund(refundRequest);
		
		content.addReturnValue(PAYMENTS_KEY, refundRequest);
		content.setViewName(paymentServiceProxy.getRefundView(refundRequest));
	}

}
