package com.sharingif.cube.components.payments.pay;

import java.util.Map;

import com.sharingif.cube.components.payments.Payment;


/**
 * [支付服务]
 * [2015年4月25日 下午10:06:50]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public interface IPayService<P extends Payment> {
	
	/**
	 * 转换支付数据
	 * @param payRequest : 支付数据
	 * @return 返回新的支付对象
	 */
	P convertPay(Payment payRequest);
	
	/**
	 * 对支付数据进行签名
	 * @param paymentId : 支付id
	 * @param privateKey : 私钥
	 * @param payRequest : 支付数据
	 * @return 签名数据
	 */
	String sign(String paymentId, Object privateKey, P payRequest);
	
	/**
	 * 处理支付数据
	 * @param paymentId : 支付id
	 * @param signDate : 签名数据
	 * @param payRequest : 支付数据
	 */
	void handlePay(String paymentId, String signDate, P payRequest);
	
	/**
	 * 转换支付回调数据
	 * @param payResponseMap : 支付回调数据
	 * @return
	 */
	P convertCallBackData(Map<String,Object> payResponseMap);
	
	/**
	 * 验证支付返回数据
	 * @param publicKeyCode : 公钥
	 * @param payResponse : 支付返回数据
	 * @return 验签结果
	 */
	boolean verify(Object publicKey, P payResponse);
	
	/**
	 * 获取回调签名数据
	 * @param payResponse : 支付返回数据
	 * @return
	 */
	String getCallBackSignatureData(P payResponse);
	
	/**
	 * 获取回调支付id
	 * @param payResponse : 支付返回数据
	 * @return
	 */
	String getCallBackPaymentId(P payResponse);
	
	/**
	 * 支付回调
	 * @param payResponse : 支付返回数据
	 * @return 支付结果
	 */
	boolean handleCallBackPay(P payResponse);

}
