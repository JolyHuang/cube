package com.sharingif.cube.core.exception.handler;

import java.util.Locale;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.request.RequestContext;


/**   
 *  
 * @Description:  [异常处理器]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年2月20日 下午9:38:52]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年2月20日 下午9:38:52]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface IExceptionHandler<RI extends RequestContext<?>,H extends Object> {
	
	/**
	 * 判断是否支持
	 * @param exception
	 * @return
	 */
	boolean supports(Exception exception);
	
	/**
	 * 将Exception转换为ICubeException
	 * @param exception
	 * @return
	 */
	ICubeException convertException(Exception exception);
	
	/**
	 * 解析cubeException获得特定于语言环境的消息
	 * @param cubeException : cube异常
	 * @param locale : 语言环境
	 * @return 
	 */
	void resolverMessages(ICubeException cubeException,Locale locale);
	
	/**
	 * 输出日志
	 * @param requestContext : 请求信息
	 * @param handler : 请求处理器
	 * @param cubeException : cube异常
	 */
	void wirteLog(RI requestContext, H handler, ICubeException cubeException);
	
	/**
	 * 处理异常
	 * @param requestContext : 请求信息
	 * @param handler : 请求处理器
	 * @param cubeException : cube异常
	 * @return O : 异常处理结果
	 */
	ExceptionContent handlerException(RI requestContext, H handler, ICubeException cubeException);

}
