package com.sharingif.cube.core.request;

/**
 * 请求信息解析为RequestInfo对象
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface RequestInfoResolver<I,O extends RequestInfo<?>> {
	
	/**
	 * 请求信息解析为RequestInfo对象
	 * @param request : 请求信息
	 * @return
	 */
	O resolveRequest(I request);

}
