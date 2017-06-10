package com.sharingif.cube.components.payments.chinapay.p2b.refund;

import java.util.Map;

import chinapay.SecureLink;

import com.sharingif.cube.components.payments.Payment;
import com.sharingif.cube.components.payments.refund.IRefundService;

/**
 * [银联退款]
 * [2015年5月12日 下午10:09:53]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 * @param <T>
 */
public class ChinaPayRefundService implements IRefundService<ChinaPayRefund> {
	
	@Override
	public ChinaPayRefund convertPay(Payment refundRequest) {
		return null;
	}
	
	@Override
	public String sign(String paymentId, Object privateSecret, ChinaPayRefund refundRequest) {
		return ((SecureLink) privateSecret).Sign(new StringBuilder()
		.append(refundRequest.getMerId())
		.append(refundRequest.getTransDate())
		.append(refundRequest.getTransType())
		.append(paymentId)
		.append(refundRequest.getRefundAmount())
		.append(refundRequest.getPriv1())
		.toString());
	}
	@Override
	public void handleRefund(String paymentId, String signDate, ChinaPayRefund refundRequest) {
		refundRequest.setOrderId(paymentId);
		refundRequest.setChkValue(signDate);
	}
	
	@Override
	public ChinaPayRefund convertCallBackData(Map<String, Object> refundResponseMap) {
		return null;
	}
	
	@Override
	public boolean verify(Object publicSecret, ChinaPayRefund refundResponse) {
		return ((SecureLink)publicSecret).verifyTransResponse(
				refundResponse.getMerId(),
				refundResponse.getOrderId(),
				refundResponse.getRefundAmount(),
				refundResponse.getCuryId(),
				refundResponse.getTransDate(),
				refundResponse.getTransType(),
				refundResponse.getStatus(),
				refundResponse.getChkValue());
	}
	
	@Override
	public String getCallBackSignatureData(ChinaPayRefund refundResponse) {
		return refundResponse.getChkValue();
	}
	
	@Override
	public String getCallBackPaymentBatchNum(ChinaPayRefund payResponse) {
		return payResponse.getOrderId();
	}
	
	@Override
	public boolean handleCallBackRefund(ChinaPayRefund refundResponse) {
		boolean refundStatus = false;
		//ResponseCode为0，且Status为3或8的时候，需要对chinapay返回数据进行验签。
		if("0".equals(refundResponse.getResponseCode()) || "3".equals(refundResponse.getStatus()) || "8".equals(refundResponse.getStatus())){
			if("3".equals(refundResponse.getStatus())){
				refundStatus = true;
			}
		}
		
		return refundStatus;
	}
	
	
	private String refundView;
	/**
	 * 这里action的内容为提交退款数据的URL地址
	 */
	private String refundAction;
	/**
	 * 交易类型：0002为退款请求
	 */
	private String refundTransType;
	/**
	 * 单笔退款接口版本号，必填
	 */
	private String refundVersion;
	/**
	 * 退款状态接收URL，可选，长度不要超过80个字节
	 */
	private String returnURL;
	
	public String getRefundView() {
		return refundView;
	}

	public void setRefundView(String refundView) {
		this.refundView = refundView;
	}

	public String getRefundAction() {
		return refundAction;
	}

	public void setRefundAction(String refundAction) {
		this.refundAction = refundAction;
	}

	public String getRefundTransType() {
		return refundTransType;
	}

	public void setRefundTransType(String refundTransType) {
		this.refundTransType = refundTransType;
	}

	public String getRefundVersion() {
		return refundVersion;
	}

	public void setRefundVersion(String refundVersion) {
		this.refundVersion = refundVersion;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	
}
