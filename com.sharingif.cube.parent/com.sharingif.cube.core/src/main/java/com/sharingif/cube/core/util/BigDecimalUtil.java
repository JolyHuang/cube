package com.sharingif.cube.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**   
 *  
 * @Description:  [BigDecimal 工具箱]   
 * @Author:       [Joly]   
 * @CreateDate:   [2015年3月26日 下午11:01:51]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2015年3月26日 下午11:01:51]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class BigDecimalUtil {
	
	/**
	 * 比较前一个值是否大于后一个值
	 * @param value1 : 前一个值
	 * @param value1 : 后一个值
	 * @return
	 */
	public static boolean isGreaterThan(BigDecimal value1, BigDecimal value2){
		return value1.compareTo(value2)>0;
	}
	
	/**
	 * 判断是否大于0
	 * @param value : 值
	 * @return
	 */
	public static boolean isGreaterThanZero(BigDecimal value){
		return isGreaterThan(value, BigDecimal.ZERO);
	}

	/**
	 * 判断是否等于空或零
	 * @param value : 值
	 * @return
	 */
	public static boolean isNullOrZero(BigDecimal value){
		return (value == null || value.compareTo(BigDecimal.ZERO)==0);
	}
	
	/**
	 * 负数转正数
	 * @param value : 值
	 * @return
	 */
	public static BigDecimal convertToPositive(BigDecimal value){
		if(!isGreaterThanZero(value)){
			return BigDecimal.ZERO.subtract(value);
		}
		return value;
	}
	
	/**
	 * 正数转负数
	 * @param value : 值
	 * @return
	 */
	public static BigDecimal convertToNegative(BigDecimal value){
		if(isGreaterThanZero(value)){
			return BigDecimal.ZERO.subtract(value);
		}
		return value;
	}

	/**
	 * 金额格式化
	 * @param amount ： 金额
	 * @param pattern : 格式
	 * @return
	 */
	public static String formatDecimal(BigDecimal amount, String pattern) {
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(amount);
	}

}
