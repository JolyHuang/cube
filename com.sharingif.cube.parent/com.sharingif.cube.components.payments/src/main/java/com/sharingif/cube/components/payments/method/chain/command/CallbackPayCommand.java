package com.sharingif.cube.components.payments.method.chain.command;

import java.util.HashMap;
import java.util.Map;

import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.components.payments.Payment;
import com.sharingif.cube.components.payments.PaymentServiceProxy;
import com.sharingif.cube.components.payments.ResponsePayment;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.web.method.chain.command.AbstractWebHandlerMethodCommand;

/**
 *
 * @Description:  [支付回调命令]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月8日 下午6:18:12]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月8日 下午6:18:12]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class CallbackPayCommand extends AbstractWebHandlerMethodCommand {
	
	private PaymentServiceProxy<Payment, Payment> paymentServiceProxy;

	public PaymentServiceProxy<Payment, Payment> getPaymentServiceProxy() {
		return paymentServiceProxy;
	}
	public void setPaymentServiceProxy(PaymentServiceProxy<Payment, Payment> paymentServiceProxy) {
		this.paymentServiceProxy = paymentServiceProxy;
	}
	
	protected ResponsePayment getResponsePayment(Map<String,Object> responseMap) {
		return paymentServiceProxy.callBackPay(responseMap);
	}

	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		Object[] args = content.getArgs();
		
		Map<String,Object> payResponseMap = new HashMap<String,Object>();
		payResponseMap.put(Payment.PAYMENT_TYPE, args[0]);
		
		Map<String,String[]> requestParams = content.getRequest().getParameterMap();
		for(Map.Entry<String, String[]> entry : requestParams.entrySet()){
			String name = entry.getKey();
			String[] values = entry.getValue();
			StringBuilder valueStr = new StringBuilder();
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr.append(values[i]) 
						: valueStr.append(values[i]).append(",");
			}
			payResponseMap.put(name, new String(valueStr.toString()));
		}
		
		ResponsePayment responsePayment = getResponsePayment(payResponseMap);
		args[1] = responsePayment;
		
		content.setArgs(args);
	}
	
}
