package com.sharingif.cube.components.payments;

import com.sharingif.cube.components.payments.format.AbstractPaymentFormat;

/**
 * [流水服务]
 * [2015年5月7日 下午11:52:19]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public interface IPaymentJournalService<T extends PaymentJournal> {
	
	/**
	 * 根据支付数据查询是否有支付流水
	 * @param payment : 支付数据
	 * @return
	 */
	T getPaymentJournal(Payment payment);
	
	/**
	 * 是否重复提交
	 * @param payment : 支付数据
	 * @param paymentJournal : 支付流水
	 */
	boolean isDuplicateSubmission(Payment payment, T paymentJournal);
	
	/**
	 * 返回支付流水中的支付流水号
	 * @param paymentJournal : 支付流水
	 * @return
	 */
	String getPaymentId(T paymentJournal);
	
	/**
	 * 是否重复回调
	 * @param paymentId : 支付id
	 * @param payment : 回调支付数据
	 */
	boolean isDuplicateCallBack(String paymentId, Payment payment);
	
	/**
	 * 注入支付数据格式化对象
	 * @param paymentFormat
	 */
	void setPaymentFormat(AbstractPaymentFormat paymentFormat);
	
	/**
	 * 获取支付流水
	 * @param paymentId ： 支付id
	 * @param signData ： 签名数据
	 * @param payRequest ： 支付数据
	 * @return 支付流水
	 */
	PaymentJournal getPayJournal(String paymentId, String signData, Payment payRequest);
	
	/**
	 * 处理支付流水
	 * @param payPaymentJournal ： 支付流水
	 */
	void payJournal(T payPaymentJournal);
	
	/**
	 * 获取支付回调流水
	 * @param paymentId : 支付id
	 * @param payStatus : 支付状态
	 * @param signatureData : 签名数据
	 * @param payResponse : 支付返回数据
	 * @return 支付流水
	 */
	PaymentJournal getCallBackPayJournal(String paymentId, boolean payStatus, String signatureData, Payment payResponse);
	
	/**
	 * 处理支付回调流水
	 * @param callBackPaymentJournal : 支付返回流水
	 */
	void callBackPayJournal(T callBackPaymentJournal);
	
	/**
	 * 获取退款流水
	 * @param paymentId ： 支付id
	 * @param signData ： 签名数据
	 * @param refundRequest ： 退款数据
	 * @return 退款流水
	 */
	PaymentJournal getRefundJournal(String paymentId, String signData, Payment refundRequest);
	
	/**
	 * 退款流水
	 * @param refundPaymentJournal ： 退款流水
	 */
	void refundJournal(T refundPaymentJournal);
	
	/**
	 * 获取退款回调流水
	 *  @param paymentId : 支付id
	 * @param refundStatus : 退款状态
	 * @param signData ： 签名数据
	 * @param refundResponse : 退款返回数据
	 * @return 退款流水
	 */
	PaymentJournal getCallBackRefundJournal(String paymentId, boolean refundStatus, String signatureData, Payment refundResponse);
	
	/**
	 * 退款回调流水
	 * @param callBackRefundPaymentJournal : 退款返回流水
	 */
	void callBackRefundJournal(T callBackRefundPaymentJournal);
	
	/**
	 * 根据支付批次号返回订单id
	 * @param paymentId : 支付批次号
	 * @return
	 */
	String getOrderNo(String paymentId);
	
	
}
