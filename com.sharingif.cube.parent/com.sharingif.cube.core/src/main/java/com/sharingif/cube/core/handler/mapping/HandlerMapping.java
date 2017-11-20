package com.sharingif.cube.core.handler.mapping;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.request.RequestContext;



/**
 * [解析数据匹配Handler]
 * [2015年6月21日 下午5:59:37]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public interface HandlerMapping<RI,T> {
	
	/**
	 * 返回handler
	 * @param request : 请求对象
	 * @return
	 * @throws CubeException
	 */
	T getHandler(RequestContext<RI> request) throws CubeException;
	
}
