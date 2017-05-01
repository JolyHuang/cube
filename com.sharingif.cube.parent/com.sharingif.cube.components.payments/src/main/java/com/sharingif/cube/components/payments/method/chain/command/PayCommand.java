package com.sharingif.cube.components.payments.method.chain.command;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.components.payments.Payment;
import com.sharingif.cube.components.payments.PaymentServiceProxy;
import com.sharingif.cube.components.payments.RequestPayment;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.web.handler.chain.command.AbstractWebHandlerMethodCommand;

/**
 *
 * @Description:  [支付命令]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月8日 下午12:58:52]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月8日 下午12:58:52]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class PayCommand extends AbstractWebHandlerMethodCommand {
	
	private static final String PAYMENTS_KEY = "_payment";
	
	private PaymentServiceProxy<? extends Payment, ? extends Payment> paymentServiceProxy;

	public PaymentServiceProxy<? extends Payment, ? extends Payment> getPaymentServiceProxy() {
		return paymentServiceProxy;
	}
	public void setPaymentServiceProxy(PaymentServiceProxy<? extends Payment, ? extends Payment> paymentServiceProxy) {
		this.paymentServiceProxy = paymentServiceProxy;
	}

	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		Payment payRequest = content.getObject(Payment.class);
		
		RequestPayment requestPayment = paymentServiceProxy.handlePay(payRequest);
		
		content.addReturnValue(PAYMENTS_KEY, requestPayment.getPayment());
		content.setViewName(requestPayment.getRequestView());
	}

}
