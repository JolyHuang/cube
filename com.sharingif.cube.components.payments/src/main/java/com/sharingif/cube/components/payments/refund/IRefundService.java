package com.sharingif.cube.components.payments.refund;

import java.util.Map;

import com.sharingif.cube.components.payments.Payment;


/**
 *
 * @Description:  [退款服务]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月12日 上午10:10:44]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月12日 上午10:10:44]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface IRefundService<R extends Payment> {
	
	/**
	 * 转换退款数据
	 * @param refundRequest : 退款数据
	 * @return 返回新的退款对象
	 */
	R convertPay(Payment refundRequest);
	
	/**
	 * 对支付数据进行签名
	 * @param paymentId : 支付id
	 * @param privateKey : 私钥
	 * @param refundRequest : 退款数据
	 * @return 签名数据
	 */
	String sign(String paymentId, Object privateKey, R refundRequest);

	/**
	 * 处理退款数据
	 * @param paymentId : 支付id
	 * @param signDate : 签名数据
	 * @param refundRequest : 退款数据
	 */
	void handleRefund(String paymentId, String signDate, R refundRequest);
	
	/**
	 * 转换退款回调数据
	 * @param refundResponseMap : 退款回调数据
	 * @return
	 */
	R convertCallBackData(Map<String,Object> refundResponseMap);
	
	/**
	 * 验证返回数据
	 * @param publicKeyCode : 公钥
	 * @param refundResponse : 回数据
	 * @return 验签结果
	 */
	boolean verify(Object publicKey, R refundResponse);
	
	/**
	 * 获取回调签名数据
	 * @param refundResponse : 退款返回数据
	 * @return
	 */
	String getCallBackSignatureData(R refundResponse);
	
	/**
	 * 获取回调支付id
	 * @param payResponse : 支付返回数据
	 * @return
	 */
	String getCallBackPaymentBatchNum(R payResponse);
	
	/**
	 * 支付回调
	 * @param refundResponse : 退款返回数据
	 * @return 退款结果
	 */
	boolean handleCallBackRefund(R refundResponse);
	
}
