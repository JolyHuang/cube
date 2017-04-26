package com.sharingif.cube.components.payments;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @Description:  [支付对象]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月8日 上午10:34:31]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月8日 上午10:34:31]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class Payment {
	
	public static final String PAYMENT_TYPE="_paymentType";
	
	public Payment(String paymentType){
		this.paymentType = paymentType;
	}
	
	public Payment(Payment payment){
		this.paymentType = payment.getPaymentType();
		this.paymentOrderId = payment.getPaymentOrderId();
		this.paymentOrderTitle = payment.getPaymentOrderTitle();
		this.paymentAmount = payment.getPaymentAmount();
		this.paymentBankCode = payment.getPaymentBankCode();
		this.paymentCurrency = payment.getPaymentCurrency();
		this.paymentTransDate = payment.getPaymentTransDate();
		this.paymentTransDate = payment.getPaymentTransDate();
		this.paymentRemarks = payment.getPaymentRemarks();
				
	}
	
	/**
	 * 支付类型
	 */
	private String paymentType;
	
	/**
	 * 交易订单号
	 */
	private String paymentOrderId;
	/**
	 * 交易订单标题
	 */
	private String paymentOrderTitle;
	/**
	 * 交易金额
	 */
	private BigDecimal paymentAmount;
	/**
	 * 支付银行代码
	 */
	private String paymentBankCode;
	/**
	 * 交易币种
	 */
	private String paymentCurrency;
	/**
	 * 交易日期
	 */
	private Date paymentTransDate = new Date();
	/**
	 * 备注
	 */
	private String paymentRemarks;
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentOrderId() {
		return paymentOrderId;
	}
	public void setPaymentOrderId(String paymentOrderId) {
		this.paymentOrderId = paymentOrderId;
	}
	public String getPaymentOrderTitle() {
		return paymentOrderTitle;
	}
	public void setPaymentOrderTitle(String paymentOrderTitle) {
		this.paymentOrderTitle = paymentOrderTitle;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentBankCode() {
		return paymentBankCode;
	}
	public void setPaymentBankCode(String paymentBankCode) {
		this.paymentBankCode = paymentBankCode;
	}
	public String getPaymentCurrency() {
		return paymentCurrency;
	}
	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}
	public Date getPaymentTransDate() {
		return paymentTransDate;
	}
	public void setPaymentTransDate(Date paymentTransDate) {
		this.paymentTransDate = paymentTransDate;
	}
	public String getPaymentRemarks() {
		return paymentRemarks;
	}
	public void setPaymentRemarks(String paymentRemarks) {
		this.paymentRemarks = paymentRemarks;
	}


	@Override
	public String toString() {
		return new StringBuilder("Payment [")
		.append("paymentType=").append(getPaymentType()).append(", ")
		.append("paymentOrderId=").append(getPaymentOrderId()).append(", ")
		.append("paymentOrderTitle=").append(getPaymentOrderTitle()).append(", ")
		.append("paymentBankCode=").append(getPaymentBankCode()).append(", ")
		.append("paymentAmount=").append(getPaymentAmount()).append(", ")
		.append("paymentCurrency=").append(getPaymentCurrency()).append(", ")
		.append("paymentTransDate=").append(getPaymentTransDate()).append(", ")
		.append("paymentRemarks=").append(getPaymentRemarks())
		.append("]").toString();
	}
	
}
