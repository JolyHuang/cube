package com.sharingif.cube.core.method.handler.adapter;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * [适配Handler]
 * [2015年6月21日 下午6:25:34]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public interface HandlerAdapter<H> {
	
	/**
	 * Given a handler instance, return whether or not this HandlerAdapter can
	 * support it. Typical HandlerAdapters will base the decision on the handler
	 * type. HandlerAdapters will usually only support one handler type each.
	 * <p>A typical implementation:
	 * <p>{@code
	 * return (handler instanceof MyHandler);
	 * }
	 * @param handler handler object to check
	 * @return whether or not this object can use the given handler
	 */
	boolean supports(Object handler);
	
	/**
	 * 执行handler
	 * @param request : 请求对象
	 * @param handler : 处理器
	 * @return
	 * @throws CubeException
	 */
	Object handle(RequestInfo<?> request, H handler)throws CubeException;

}
