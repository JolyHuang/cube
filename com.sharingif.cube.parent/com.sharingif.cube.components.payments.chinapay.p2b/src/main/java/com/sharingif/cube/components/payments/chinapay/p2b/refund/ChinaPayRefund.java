package com.sharingif.cube.components.payments.chinapay.p2b.refund;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.sharingif.cube.components.payments.Payment;
import com.sharingif.cube.components.payments.chinapay.p2b.pay.ChinaPayPay;

/**
 *
 * @Description:  [银联退款对象]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月8日 上午10:52:18]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月8日 上午10:52:18]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class ChinaPayRefund extends Payment{

	/**
	 * 金额格式化类型
	 */
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("000000000000");
	/**
	 * 金额转换比例(分)
	 */
	public static final BigDecimal TRANS_AMT_SCALE = new BigDecimal(100);
	
	public ChinaPayRefund(Payment payment){
		super(payment);
		orderId = payment.getPaymentOrderId();
		refundAmount = ChinaPayPay.formatAmountYuanToFen(payment.getPaymentAmount());
		transDate = ChinaPayPay.getFomatDate(payment.getPaymentTransDate());
		priv1 = payment.getPaymentRemarks();
	}
	
	/**
	 * 这里action的内容为提交退款数据的URL地址
	 */
	private String action;
	/**
	 * MerId为ChinaPay统一分配给商户的商户号，15位长度，必填
	 */
	private String merId;
	/**
	 * 交易类型：0002为退款请求
	 */
	private String transType;
	/**
	 * 原始支付订单号，16位长度，必填
	 */
	private String orderId;
	/**
	 * 商户退款金额，单位为分，12位长度，不足左补0，必填
	 */
	private String refundAmount;
	/**
	 * 订单交易币种，3位长度，固定为人民币156， 必填
	 */
	private String curyId;
	/**
	 * 原交易日期，8位长度，必填
	 */
	private String transDate;
	/**
	 * 单笔退款接口版本号，必填
	 */
	private String version;
	/**
	 * 退款状态接收URL，可选，长度不要超过80个字节
	 */
	private String returnURL;
	/**
	 * 商户私有域，必填，长度不要超过40个字节
	 */
	private String priv1;
	/**
	 * 256字节长的ASCII码，必填
	 */
	private String chkValue;
	
	private String status;
	
	private String responseCode;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getCuryId() {
		return curyId;
	}
	public void setCuryId(String curyId) {
		this.curyId = curyId;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	public String getPriv1() {
		return priv1;
	}
	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}
	public String getChkValue() {
		return chkValue;
	}
	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	
	@Override
	public String toString() {
		
		return new StringBuilder("UnionPayRefundOrder [")
		.append("action=").append(action).append(", ")
		.append("merId=").append(merId).append(", ")
		.append("transType=").append(transType).append(", ")
		.append("orderId=").append(orderId).append(", ")
		.append("refundAmount=").append(refundAmount).append(", ")
		.append("transDate=").append(transDate).append(", ")
		.append("versiong=").append(version).append(", ")
		.append("returnURL=").append(returnURL).append(", ")
		.append("priv1=").append(priv1).append(", ")
		.append("chkValue=").append(chkValue)
		.append("]").toString();
	}
	
}
