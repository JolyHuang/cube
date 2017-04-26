package com.sharingif.cube.core.exception.handler;

import com.sharingif.cube.core.request.RequestInfo;

/**
 * 异常解析器
 * 2016年5月15日 下午6:17:48
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface IExceptionResolver<RI,O extends ExceptionContent,H extends Object> {

	/**
	 * 处理异常
	 * @param requestInfo : 请求信息
	 * @param handler : 请求处理器
	 * @param exception : 异常
	 * @param locale : 语言环境
	 * @return O : 异常处理结果
	 */
	O resolverException(RequestInfo<RI> requestInfo, H handler, Exception exception);
	
}
