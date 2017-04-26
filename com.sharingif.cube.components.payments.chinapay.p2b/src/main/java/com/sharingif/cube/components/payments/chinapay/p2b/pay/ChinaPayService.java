package com.sharingif.cube.components.payments.chinapay.p2b.pay;

import java.util.Map;

import chinapay.SecureLink;

import com.sharingif.cube.components.payments.Payment;
import com.sharingif.cube.components.payments.pay.IPayService;

/**
 * [银联支付]
 * [2015年4月26日 下午10:57:24]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class ChinaPayService implements IPayService<ChinaPayPay> {
	
	/**
	 * 银联
	 */
	public static final String UNIONPAY="01";
	
	
	@Override
	public ChinaPayPay convertPay(Payment payRequest) {
		ChinaPayPay unionPay = new ChinaPayPay(payRequest);
		
		unionPay.setAction(payAction);
		unionPay.setMerId(getMerId());
		unionPay.setCuryId(getCuryId());
		payRequest.setPaymentCurrency(getCuryId());
		unionPay.setTransType(getPayTransType());
		unionPay.setVersion(getPayVersion());
		unionPay.setBgRetUrl(getBgRetUrl());
		unionPay.setPageRetUrl(getPageRetUrl());
		unionPay.setGateId(getGateId());
		
		return unionPay;
	}
	
	@Override
	public String sign(String paymentId, Object privateSecret, ChinaPayPay payRequest) {
		
		return ((SecureLink) privateSecret).Sign(new StringBuilder()
		.append(payRequest.getMerId())
		.append(paymentId)
		.append(payRequest.getTransAmt())
		.append(payRequest.getCuryId())
		.append(payRequest.getTransDate())
		.append(payRequest.getTransType())
		.append(payRequest.getVersion())
		.append(payRequest.getGateId())
		.append(payRequest.getBgRetUrl())
		.append(payRequest.getPageRetUrl())
		.append(payRequest.getPriv1())
		.append(payRequest.getClientIp())
		.toString());
	}

	@Override
	public void handlePay(String paymentId, String signDate, ChinaPayPay payRequest) {
		payRequest.setOrdId(paymentId);
		payRequest.setChkValue(signDate);
	}
	
	@Override
	public ChinaPayPay convertCallBackData(Map<String, Object> payResponseMap) {
		String merId = (String)payResponseMap.get("merid");
		String orderNo = (String)payResponseMap.get("orderno");
		String transDate = (String)payResponseMap.get("transdate");
		String amount = (String)payResponseMap.get("amount");
		String currencyCode = (String)payResponseMap.get("currencycode");
		String transType = (String)payResponseMap.get("transtype");
		String status = (String)payResponseMap.get("status");
		String checkValue = (String)payResponseMap.get("checkvalue");
		String gateId = (String)payResponseMap.get("GateId");
		String priv1 = (String)payResponseMap.get("Priv1");
		
		ChinaPayPay chinaPayPay = new ChinaPayPay();
		
		chinaPayPay.setMerId(merId);
		chinaPayPay.setPaymentOrderId(orderNo);
		chinaPayPay.setTransDate(transDate);
		chinaPayPay.setTransAmtFenToYuan(amount);
		chinaPayPay.setCuryId(currencyCode);
		chinaPayPay.setTransType(transType);
		chinaPayPay.setPayStatus(status);
		chinaPayPay.setChkValue(checkValue);
		chinaPayPay.setGateId(gateId);
		chinaPayPay.setPriv1(priv1);
		
		
		return chinaPayPay;
	}

	@Override
	public boolean verify(Object publicSecret, ChinaPayPay payResponse) {
		return ((SecureLink)publicSecret).verifyTransResponse(
				payResponse.getMerId(),
				payResponse.getOrdId(),
				payResponse.getTransAmt(),
				payResponse.getCuryId(),
				payResponse.getTransDate(),
				payResponse.getTransType(),
				payResponse.getPayStatus(),
				payResponse.getCallBackCheckValue());
	}
	
	@Override
	public String getCallBackSignatureData(ChinaPayPay payResponse) {
		return payResponse.getCallBackCheckValue();
	}
	
	@Override
	public String getCallBackPaymentId(ChinaPayPay payResponse) {
		return payResponse.getOrdId();
	}

	@Override
	public boolean handleCallBackPay(ChinaPayPay payResponse) {
		boolean paymentStatus = false;
		if("1001".equals(payResponse.getPayStatus())){
			paymentStatus = true;
		}
		return paymentStatus;
	}
	
	/**
	 * MerId为ChinaPay统一分配给商户的商户号，15位长度，必填
	 */
	private String merId;
	/**
	 * 这里action的内容为提交付款数据的URL地址
	 */
	private String payAction;
	/**
	 * 订单交易币种，3位长度，固定为人民币156 必填
	 */
	private String curyId;
	/**
	 * 支付网关号，可选
	 */
	private String gateId;
	/**
	 * 交易类型：0001为付款请求
	 */
	private String payTransType;
	/**
	 * 单笔退款接口版本号，必填
	 */
	private String payVersion;
	/**
	 * 后台交易接收URL，长度不要超过80个字节，必填
	 */
	private String bgRetUrl;
	/**
	 * 页面交易接收URL，长度不要超过80个字节，必填
	 */
	private String pageRetUrl;


	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getPayAction() {
		return payAction;
	}

	public void setPayAction(String payAction) {
		this.payAction = payAction;
	}

	public String getCuryId() {
		return curyId;
	}

	public void setCuryId(String curyId) {
		this.curyId = curyId;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getPayTransType() {
		return payTransType;
	}

	public void setPayTransType(String payTransType) {
		this.payTransType = payTransType;
	}

	public String getPayVersion() {
		return payVersion;
	}

	public void setPayVersion(String payVersion) {
		this.payVersion = payVersion;
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

}
