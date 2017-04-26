package com.sharingif.cube.components.payments.chinapay.p2b.pay;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.sharingif.cube.components.payments.Payment;
import com.sharingif.cube.core.util.DateUtils;
import com.sharingif.cube.core.util.StringUtils;

/**
 *
 * @Description:  [银联支付对象]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月8日 上午10:51:52]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月8日 上午10:51:52]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class ChinaPayPay extends Payment{
	
	/**
	 * 金额格式化类型
	 */
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("000000000000");
	/**
	 * 金额转换比例(分)
	 */
	public static final BigDecimal TRANS_AMT_SCALE = new BigDecimal(100);
	
	public static final String PAYMENT_TYPE = "000";
	
	public ChinaPayPay(){
		super(PAYMENT_TYPE);
	}
	
	public ChinaPayPay(Payment payment){
		super(payment);
		setTransAmtYuanToFen(payment.getPaymentAmount());
		transDate = getFomatDate(payment.getPaymentTransDate());
		
		if(StringUtils.isEmpty(payment.getPaymentRemarks())){
			payment.setPaymentRemarks(payment.getPaymentOrderId());
		}
			
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
	 * 原始支付订单号，16位长度，必填
	 */
	private String ordId;
	/**
	 * 订单交易金额，12位长度，左补0，必填,单位为分
	 */
	private String transAmt;
	/**
	 * 订单交易币种，3位长度，固定为人民币156， 必填
	 */
	private String curyId;
	/**
	 * 原交易日期，8位长度，必填
	 */
	private String transDate;
	/**
	 * 交易类型，4位长度，必填
	 */
	private String transType;
	/**
	 * 支付接入版本号，必填
	 */
	private String version;
	/**
	 * 后台交易接收URL，长度不要超过80个字节，必填
	 */
	private String bgRetUrl;
	/**
	 * 页面交易接收URL，长度不要超过80个字节，必填
	 */
	private String pageRetUrl;
	/**
	 * 支付网关号，可选
	 */
	private String gateId;
	/**
	 * 商户私有域，长度不要超过60个字节
	 */
	private String priv1;
	/**
	 * 用户在商户网站上提交订单时，由商户网站获取到用户的客户端IP地址，长度不要超过15个字节,必填
	 */
	private String clientIp;
	/**
	 * 256字节长的ASCII码,为此次交易提交关键数据的数字签名，必填
	 */
	private String chkValue;
	
	
	/**
	 * 支付状态
	 */
	private String payStatus;
	/**
	 * 回调签名数据
	 */
	private String callBackCheckValue;
	/**
	 * 备注
	 */
	private String remarks;
	

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
	public String getOrdId() {
		return ordId;
	}
	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmtYuanToFen(BigDecimal transAmt) {
		this.transAmt = formatAmountYuanToFen(transAmt);
	}
	public void setTransAmtFenToYuan(String transAmt) {
		this.transAmt = formatAmountYuanToFen(new BigDecimal(transAmt));
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
	public void setTransDate(Date transDate) {
		this.transDate = getFomatDate(transDate);
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getBgRetUrl() {
		return bgRetUrl;
	}
	public void setBgRetUrl(String bgRetUrl) {
		this.bgRetUrl = bgRetUrl;
	}
	public String getPageRetUrl() {
		return pageRetUrl;
	}
	public void setPageRetUrl(String pageRetUrl) {
		this.pageRetUrl = pageRetUrl;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getPriv1() {
		return priv1;
	}
	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getChkValue() {
		return chkValue;
	}
	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	public static String formatAmountYuanToFen(BigDecimal amount){
		return DECIMAL_FORMAT.format(amount.multiply(TRANS_AMT_SCALE).longValue());
	}
	public static BigDecimal formatAmountFenToYuan(String amount){
		return new BigDecimal(amount).divide(TRANS_AMT_SCALE);
	}
	public static String getFomatDate(Date transDate){
		return DateUtils.getDate(transDate, DateUtils.DATE_COMPACT_FORMAT);
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getCallBackCheckValue() {
		return callBackCheckValue;
	}
	public void setCallBackCheckValue(String callBackCheckValue) {
		this.callBackCheckValue = callBackCheckValue;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Override
	public String toString() {
		
		return new StringBuilder("UnionPayPayOrder [")
		.append("action=").append(action).append(", ")
		.append("merId=").append(merId).append(", ")
		.append("ordId=").append(ordId).append(", ")
		.append("transAmt=").append(transAmt).append(", ")
		.append("curyId=").append(curyId).append(", ")
		.append("transDate=").append(transDate).append(", ")
		.append("transType=").append(transType).append(", ")
		.append("version=").append(version).append(", ")
		.append("bgRetUrl=").append(bgRetUrl).append(", ")
		.append("pageRetUrl=").append(pageRetUrl).append(", ")
		.append("gateId=").append(gateId).append(", ")
		.append("priv1=").append(priv1).append(", ")
		.append("chkValue=").append(chkValue).append(", ")
		.append("payStatus=").append(payStatus).append(", ")
		.append("callBackCheckValue=").append(callBackCheckValue).append(", ")
		.append("remarks=").append(remarks)
		.append("]").toString();
	}
	

}
