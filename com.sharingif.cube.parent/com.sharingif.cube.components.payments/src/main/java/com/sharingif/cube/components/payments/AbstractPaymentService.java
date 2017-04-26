package com.sharingif.cube.components.payments;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.components.payments.pay.IPayService;
import com.sharingif.cube.components.payments.refund.IRefundService;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.util.StringUtils;

/**
 * [抽象支付服务]
 * [2015年4月26日 下午9:25:32]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public abstract class AbstractPaymentService<P extends Payment, R extends Payment> implements IPaymentService<P, R> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Object privateSecret;
	private Object publicSecret;
	
	@Override
	public boolean support(Payment payment) {
		return getPaymentType().equals(payment.getPaymentType());
	}
	
	@Override
	public RequestPayment handlePay(Payment payRequest) {
		
		PaymentJournal oldPaymentJournal = getPaymentJournalService().getPaymentJournal(payRequest);
		
		if(getPaymentJournalService().isDuplicateSubmission(payRequest, getPaymentJournalService().getPaymentJournal(payRequest))){
			throw new ValidationCubeException("pay duplicate submission");
		}
		
		String paymentId = getPaymentJournalService().getPaymentId(oldPaymentJournal);
		if(StringUtils.isEmpty(paymentId))
			paymentId = getPaymentJournalIdGenerator().generatePaymentId();
		
		P newPayRequest = payService.convertPay(payRequest);
		String signData = payService.sign(paymentId, privateSecret, newPayRequest);
		payService.handlePay(paymentId, signData, newPayRequest);
		
		PaymentJournal paymentJournal = getPaymentJournalService().getPayJournal(paymentId, signData, newPayRequest);
		
		getPaymentJournalService().payJournal(paymentJournal);
		
		RequestPayment requestPayment = new RequestPayment();
		requestPayment.setPayment(newPayRequest);
		requestPayment.setRequestView(getPayView());
		
		return requestPayment;
	}
	@Override
	public ResponsePayment callBackPay(Map<String,Object> payResponseMap) {
		
		try {
			P payResponse = payService.convertCallBackData(payResponseMap);
			
			if(!payService.verify(publicSecret, payResponse)){
				throw new ValidationCubeException("pay response verify failure");
			}
			
			String paymentId = getPayService().getCallBackPaymentId(payResponse);
			boolean duplicateCallBack = getPaymentJournalService().isDuplicateCallBack(paymentId, payResponse);
			if(duplicateCallBack){
				logger.error("is duplicate callBack data{}", payResponseMap);
				throw new ValidationCubeException("is duplicate callBack");
			}
			
			boolean payStatus = payService.handleCallBackPay(payResponse);
			
			String signatureData = getPayService().getCallBackSignatureData(payResponse);
			
			ResponsePayment responsePayment = new ResponsePayment();
			responsePayment.setPaymentId(paymentId);
			responsePayment.setPayStatus(payStatus);
			responsePayment.setPayment(payResponse);
			responsePayment.setResponseView(getCallbackPayView());
			
			PaymentJournal callBackPaymentJournal = getPaymentJournalService().getCallBackPayJournal(paymentId, payStatus, signatureData, payResponse);
			
			getPaymentJournalService().callBackPayJournal(callBackPaymentJournal);
			
			String orderNo = getPaymentJournalService().getOrderNo(paymentId);
			responsePayment.setOrderNo(orderNo);
			
			return responsePayment; 
		} catch (Exception e) {
			logger.error("callBack pay error data{}", payResponseMap);
			
			if(e instanceof CubeRuntimeException)
				throw (CubeRuntimeException)e;
				
			throw new CubeRuntimeException("callBack pay error", e);
		}
	}
	
	@Override
	public RequestPayment handleRefund(R refundRequest) {
		
		PaymentJournal oldPaymentJournal = getPaymentJournalService().getPaymentJournal(refundRequest);
		
		if(getPaymentJournalService().isDuplicateSubmission(refundRequest, oldPaymentJournal)){
			throw new ValidationCubeException("refund duplicate submission");
		}
		
		String paymentId = getPaymentJournalService().getPaymentId(oldPaymentJournal);
		if(StringUtils.isEmpty(paymentId))
			paymentId = getPaymentJournalIdGenerator().generatePaymentId();
		
		R newRefundRequest = getRefundService().convertPay(refundRequest);
		String signData = getRefundService().sign(paymentId, privateSecret, newRefundRequest);
		getRefundService().handleRefund(paymentId, signData, newRefundRequest);
		
		PaymentJournal refundPaymentJournal = getPaymentJournalService().getRefundJournal(paymentId, signData, newRefundRequest);
		
		getPaymentJournalService().refundJournal(refundPaymentJournal);
		
		RequestPayment requestPayment = new RequestPayment();
		requestPayment.setPayment(newRefundRequest);
		requestPayment.setRequestView(getRefundView());
		
		return requestPayment;
	}
	
	@Override
	public ResponsePayment callBackRefund(Map<String,Object> refundResponseMap) {
		
		try {
			R refundResponse = getRefundService().convertCallBackData(refundResponseMap);
			
			if(!getRefundService().verify(publicSecret, refundResponse)){
				throw new ValidationCubeException("refund response verify failure");
			}
			
			String paymentId = getRefundService().getCallBackPaymentBatchNum(refundResponse);
			boolean duplicateCallBack = getPaymentJournalService().isDuplicateCallBack(paymentId, refundResponse);
			if(duplicateCallBack){
				logger.error("is duplicate callBack data{}", refundResponseMap);
				throw new ValidationCubeException("is duplicate callBack");
			}
			
			boolean refundStatus = getRefundService().handleCallBackRefund(refundResponse);
			
			String signatureData = getRefundService().getCallBackSignatureData(refundResponse);
			
			ResponsePayment responsePayment = new ResponsePayment();
			responsePayment.setPaymentId(paymentId);
			responsePayment.setPayStatus(refundStatus);
			responsePayment.setPayment(refundResponse);
			responsePayment.setResponseView(getCallbackRefundView());
			
			PaymentJournal callBackRefundPaymentJournal = getPaymentJournalService().getCallBackRefundJournal(paymentId, refundStatus, signatureData, refundResponse);
			
			getPaymentJournalService().callBackRefundJournal(callBackRefundPaymentJournal);
			
			String orderNo = getPaymentJournalService().getOrderNo(paymentId);
			responsePayment.setOrderNo(orderNo);
			
			return responsePayment;
		} catch (Exception e) {
			logger.error("callBack refund error data{}", refundResponseMap);
			
			if(e instanceof CubeRuntimeException)
				throw (CubeRuntimeException)e;
			
			throw new CubeRuntimeException("callBack refund error", e);
		}
	}
	
	private IPayService<P> payService;
	private IRefundService<R> refundService;
	private PaymentIdGenerator paymentJournalIdGenerator;
	private IPaymentJournalService<? super PaymentJournal> paymentJournalService;
	private String payView;
	private String callbackPayView;
	private String refundView;
	private String callbackRefundView;
	
	public IPayService<P> getPayService() {
		return payService;
	}
	public void setPayService(IPayService<P> payService) {
		this.payService = payService;
	}
	public IRefundService<R> getRefundService() {
		return refundService;
	}
	public void setRefundService(IRefundService<R> refundService) {
		this.refundService = refundService;
	}
	public PaymentIdGenerator getPaymentJournalIdGenerator() {
		return paymentJournalIdGenerator;
	}
	public void setPaymentJournalIdGenerator(PaymentIdGenerator paymentJournalIdGenerator) {
		this.paymentJournalIdGenerator = paymentJournalIdGenerator;
	}
	public IPaymentJournalService<? super PaymentJournal> getPaymentJournalService() {
		return paymentJournalService;
	}
	public void setPaymentJournalService(IPaymentJournalService<? super PaymentJournal> paymentJournalService) {
		this.paymentJournalService = paymentJournalService;
	}

	public String getPayView() {
		return payView;
	}

	public void setPayView(String payView) {
		this.payView = payView;
	}

	public String getCallbackPayView() {
		return callbackPayView;
	}

	public void setCallbackPayView(String callbackPayView) {
		this.callbackPayView = callbackPayView;
	}

	public String getRefundView() {
		return refundView;
	}

	public void setRefundView(String refundView) {
		this.refundView = refundView;
	}

	public String getCallbackRefundView() {
		return callbackRefundView;
	}

	public void setCallbackRefundView(String callbackRefundView) {
		this.callbackRefundView = callbackRefundView;
	}

	public Object getPrivateSecret() {
		return privateSecret;
	}

	public void setPrivateSecret(Object privateSecret) {
		this.privateSecret = privateSecret;
	}

	public Object getPublicSecret() {
		return publicSecret;
	}
	public void setPublicSecret(Object publicSecret) {
		this.publicSecret = publicSecret;
	}
	
}
