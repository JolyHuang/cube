package com.sharingif.cube.core.method.handler.mapping;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.request.RequestInfo;



/**
 * [解析数据匹配Handler]
 * [2015年6月21日 下午5:59:37]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public interface HandlerMapping<T> {
	
	/**
	 * 返回handler
	 * @param request : 请求对象
	 * @return
	 * @throws CubeException
	 */
	T getHandler(RequestInfo<?> request) throws CubeException;
	
}
