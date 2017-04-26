package com.sharingif.cube.components.payments;

import java.math.BigDecimal;
import java.util.Date;

/**
 * [通用支付流水对象]
 * [2015年5月7日 下午8:42:12]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class PaymentJournal implements java.io.Serializable {
	
	private static final long serialVersionUID = -3305798932373614492L;
	
	/**
     * 交易状态(处理中)
     */	
	public static final String TRANSSTATUS_PROCESSING = "0";
	/**
     * 交易状态(交易成功)
     */	
	public static final String TRANSSTATUS_SUCCESS = "1";
	/**
     * 交易状态(交易失败)
     */	
	public static final String TRANSSTATUS_FAILURE = "2";
	
	 /**
     * 交易类型(付款)
     */	
	public static final String TRANSTYPE_PAY = "0";
	/**
     * 交易类型(退款)
     */	
	public static final String TRANSTYPE_REFUND = "1";
	
	/**
     * 对账状态(未对账)
     */	
	public static final String CHECKSTATUS_UNTREATED = "0";
	/**
     * 对账状态(对账失败)
     */	
	public static final String CHECKSTATUS_SUCCESS = "1";
	/**
     * 对账状态(对账成功)
     */	
	public static final String CHECKSTATUS_FAILURE = "2";
	
	
	 /**
     * 支付批次号			db_column: PAYMENT_BATCH_NUM 
     */	
	private String paymentBatchNum;
    /**
     * 订单编号			db_column: ORDER_NO 
     */	
	private String orderNo;
    /**
     * 交易请求时间			db_column: TRANS_REQUEST_TIME 
     */	
	private Date transRequestTime;
    /**
     * 交易请求数据类型(0:文本、1:json、2:xml)			db_column: TRANS_REQUEST_DATA_TYPE 
     */	
	private String transRequestDataType;
    /**
     * 交易请求数据			db_column: TRANS_REQUEST_DATA 
     */	
	private String transRequestData;
    /**
     * 交易请求签名数据			db_column: TRANS_REQUEST_SIGNATURE_DATA 
     */	
	private String transRequestSignatureData;
    /**
     * 交易响应时间			db_column: TRANS_RESPONSE_TIME 
     */	
	private Date transResponseTime;
    /**
     * 交易响应数据类型(0:文本、1:json、2:xml)			db_column: TRANS_RESPONSE_DATA_TYPE 
     */	
	private String transResponseDataType;
    /**
     * 交易响应数据			db_column: TRANS_RESPONSE_DATA 
     */	
	private String transResponseData;
    /**
     * 交易响应签名数据			db_column: TRANS_RESPONSE_SIGNATURE_DATA 
     */	
	private String transResponseSignatureData;
    /**
     * 交易状态(0:处理中、1:交易成功、2:交易失败)			db_column: TRANS_STATUS 
     */	
	private String transStatus;
    /**
     * 交易类型(0:付款、1:退款)			db_column: TRANS_TYPE 
     */	
	private String transType;
    /**
     * 币种(156:人名币)			db_column: CURRENCY 
     */	
	private String currency;
    /**
     * 金额			db_column: AMOUNT 
     */	
	private BigDecimal amount;
    /**
     * 支付类型(000:银联、100:支付宝)			db_column: PAYMENT_TYPE 
     */	
	private String paymentType;
    /**
     * 支付卡号			db_column: PAYMENT_CARD_NUM 
     */	
	private String paymentCardNum;
    /**
     * 对账状态(0:未对账、1:对账失败、2:对账成功)			db_column: CHECK_STATUS 
     */	
	private String checkStatus;
    /**
     * 对账次数			db_column: CHECK_NUM 
     */	
	private Integer checkNum;
	/**
     * 版本号				db_column: VERSION 
     */	
	private Integer version;

	public void setPaymentBatchNum(String paymentBatchNum) {
		this.paymentBatchNum = paymentBatchNum;
	}
	public String getPaymentBatchNum() {
		return this.paymentBatchNum;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderNo() {
		return this.orderNo;
	}
	public void setTransRequestTime(Date transRequestTime) {
		this.transRequestTime = transRequestTime;
	}
	public Date getTransRequestTime() {
		return this.transRequestTime;
	}
	public void setTransRequestDataType(String transRequestDataType) {
		this.transRequestDataType = transRequestDataType;
	}
	public String getTransRequestDataType() {
		return this.transRequestDataType;
	}
	public void setTransRequestData(String transRequestData) {
		this.transRequestData = transRequestData;
	}
	public String getTransRequestData() {
		return this.transRequestData;
	}
	public void setTransRequestSignatureData(String transRequestSignatureData) {
		this.transRequestSignatureData = transRequestSignatureData;
	}
	public String getTransRequestSignatureData() {
		return this.transRequestSignatureData;
	}
	public void setTransResponseTime(Date transResponseTime) {
		this.transResponseTime = transResponseTime;
	}
	public Date getTransResponseTime() {
		return this.transResponseTime;
	}
	public void setTransResponseDataType(String transResponseDataType) {
		this.transResponseDataType = transResponseDataType;
	}
	public String getTransResponseDataType() {
		return this.transResponseDataType;
	}
	public void setTransResponseData(String transResponseData) {
		this.transResponseData = transResponseData;
	}
	public String getTransResponseData() {
		return this.transResponseData;
	}
	public void setTransResponseSignatureData(String transResponseSignatureData) {
		this.transResponseSignatureData = transResponseSignatureData;
	}
	public String getTransResponseSignatureData() {
		return this.transResponseSignatureData;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getTransStatus() {
		return this.transStatus;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransType() {
		return this.transType;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrency() {
		return this.currency;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAmount() {
		return this.amount;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentType() {
		return this.paymentType;
	}
	public void setPaymentCardNum(String paymentCardNum) {
		this.paymentCardNum = paymentCardNum;
	}
	public String getPaymentCardNum() {
		return this.paymentCardNum;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getCheckStatus() {
		return this.checkStatus;
	}
	public void setCheckNum(Integer checkNum) {
		this.checkNum = checkNum;
	}
	public Integer getCheckNum() {
		return this.checkNum;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public void setPayJournal(Payment payment){
		orderNo = payment.getPaymentOrderId();
		transRequestTime = payment.getPaymentTransDate();
		currency = payment.getPaymentCurrency();
		amount = payment.getPaymentAmount();
		paymentType = payment.getPaymentType();
		
	}
	
	public String toString() {
		return new StringBuilder("PaymentJournal [")
			.append("PaymentBatchNum=").append(getPaymentBatchNum()).append(", ")
			.append("OrderNo=").append(getOrderNo()).append(", ")
			.append("TransRequestTime=").append(getTransRequestTime()).append(", ")
			.append("TransRequestDataType=").append(getTransRequestDataType()).append(", ")
			.append("TransRequestData=").append(getTransRequestData()).append(", ")
			.append("TransRequestSignatureData=").append(getTransRequestSignatureData()).append(", ")
			.append("TransResponseTime=").append(getTransResponseTime()).append(", ")
			.append("TransResponseDataType=").append(getTransResponseDataType()).append(", ")
			.append("TransResponseData=").append(getTransResponseData()).append(", ")
			.append("TransResponseSignatureData=").append(getTransResponseSignatureData()).append(", ")
			.append("TransStatus=").append(getTransStatus()).append(", ")
			.append("TransType=").append(getTransType()).append(", ")
			.append("Currency=").append(getCurrency()).append(", ")
			.append("Amount=").append(getAmount()).append(", ")
			.append("PaymentType=").append(getPaymentType()).append(", ")
			.append("PaymentCardNum=").append(getPaymentCardNum()).append(", ")
			.append("CheckStatus=").append(getCheckStatus()).append(", ")
			.append("CheckNum=").append(getCheckNum()).append(", ")
			.append("Version=").append(getVersion())
		.append("]").toString();
	}
	
}

