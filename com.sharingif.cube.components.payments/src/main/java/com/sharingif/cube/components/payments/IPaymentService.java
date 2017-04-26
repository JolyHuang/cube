package com.sharingif.cube.components.payments;

import java.util.Map;

/**
 * [支付服务]
 * [2015年4月25日 下午11:32:09]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public interface IPaymentService<P extends Payment, R extends Payment> {

	/**
	 * 是否支持使用
	 * @param payment : 支付信息
	 * @return
	 */
	boolean support(Payment payment);
	
	/**
	 * @return 返回支付类型
	 */
	String getPaymentType();
	
	/**
	 * 处理支付数据
	 * @param payRequest ： 支付数据
	 * @return
	 */
	RequestPayment handlePay(Payment payRequest);
	
	/**
	 * 支付回调
	 * @param payResponseMap : 支付返回数据
	 * @return
	 */
	ResponsePayment callBackPay(Map<String,Object> payResponseMap);
	
	/**
	 * 处理退款数据
	 * @param refundRequest ： 退款数据
	 * @return
	 */
	RequestPayment handleRefund(R refundRequest);
	
	
	/**
	 * 退款回调
	 * @param refundResponseMap : 退款返回数据
	 * @return
	 */
	ResponsePayment callBackRefund(Map<String,Object> refundResponseMap);
	
	/**
	 * @return 返回支付视图
	 */
	String getPayView();
	
	/**
	 * @return 返回还款视图
	 */
	String getRefundView();
	
}
