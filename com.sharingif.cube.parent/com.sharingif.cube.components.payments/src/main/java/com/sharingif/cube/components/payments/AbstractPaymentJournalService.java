package com.sharingif.cube.components.payments;

import java.util.Date;

import com.sharingif.cube.components.payments.format.AbstractPaymentFormat;

/**
 * [抽象支付流水服务]
 * [2015年5月13日 下午8:09:21]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public abstract class AbstractPaymentJournalService<T extends PaymentJournal> implements IPaymentJournalService<T> {
	
	@Override
	public PaymentJournal getPayJournal(String paymentId, String signData, Payment payRequest) {
		
		PaymentJournal paymentJournal = new PaymentJournal();
		paymentJournal.setPayJournal(payRequest);
		paymentJournal.setPaymentBatchNum(paymentId);
		paymentJournal.setTransRequestDataType(paymentFormat.getFormatType());
		paymentJournal.setTransRequestSignatureData(signData);
		paymentJournal.setTransRequestData(paymentFormat.formatPayment(payRequest));
		paymentJournal.setTransStatus(PaymentJournal.TRANSSTATUS_PROCESSING);
		paymentJournal.setTransType(PaymentJournal.TRANSTYPE_PAY);
		paymentJournal.setCheckStatus(PaymentJournal.CHECKSTATUS_UNTREATED);
		paymentJournal.setCheckNum(0);
		paymentJournal.setVersion(0);
		
		return paymentJournal;
	}
	
	@Override
	public PaymentJournal getCallBackPayJournal(String paymentId, boolean payStatus, String signatureData, Payment payResponse) {
		PaymentJournal paymentJournal = new PaymentJournal();
		paymentJournal.setPaymentBatchNum(paymentId);
		paymentJournal.setTransResponseSignatureData(signatureData);
		if(payStatus){
			paymentJournal.setTransStatus(PaymentJournal.TRANSSTATUS_SUCCESS);
		}
		else{
			paymentJournal.setTransStatus(PaymentJournal.TRANSSTATUS_FAILURE);
		}
		paymentJournal.setTransResponseDataType(getPaymentFormat().getFormatType());
		paymentJournal.setTransResponseData(getPaymentFormat().formatPayment(payResponse));
		paymentJournal.setTransResponseTime(new Date());
		
		return paymentJournal;
	}

	@Override
	public PaymentJournal getRefundJournal(String paymentId, String signData, Payment refundRequest) {
		PaymentJournal paymentJournal = new PaymentJournal();
		paymentJournal.setPayJournal(refundRequest);
		paymentJournal.setPaymentBatchNum(paymentId);
		paymentJournal.setTransRequestDataType(getPaymentFormat().getFormatType());
		paymentJournal.setTransRequestSignatureData(signData);
		paymentJournal.setTransRequestData(getPaymentFormat().formatPayment(refundRequest));
		paymentJournal.setTransStatus(PaymentJournal.TRANSSTATUS_PROCESSING);
		paymentJournal.setTransType(PaymentJournal.TRANSTYPE_PAY);
		paymentJournal.setCheckStatus(PaymentJournal.CHECKSTATUS_UNTREATED);
		paymentJournal.setCheckNum(0);
		
		return paymentJournal;
	}

	@Override
	public PaymentJournal getCallBackRefundJournal(String paymentId, boolean refundStatus, String signatureData, Payment refundResponse) {
		PaymentJournal paymentJournal = new PaymentJournal();
		paymentJournal.setPaymentBatchNum(paymentId);
		paymentJournal.setTransResponseSignatureData(signatureData);
		if(refundStatus)
			paymentJournal.setTransStatus(PaymentJournal.TRANSSTATUS_SUCCESS);
		else
			paymentJournal.setTransStatus(PaymentJournal.TRANSSTATUS_FAILURE);
		paymentJournal.setTransResponseDataType(paymentFormat.getFormatType());
		paymentJournal.setTransResponseData(paymentFormat.formatPayment(refundResponse));
		paymentJournal.setTransResponseTime(new Date());
		
		return paymentJournal;
	}
	
	private AbstractPaymentFormat paymentFormat;

	public AbstractPaymentFormat getPaymentFormat() {
		return paymentFormat;
	}
	@Override
	public void setPaymentFormat(AbstractPaymentFormat paymentFormat) {
		this.paymentFormat = paymentFormat;
	}

}
