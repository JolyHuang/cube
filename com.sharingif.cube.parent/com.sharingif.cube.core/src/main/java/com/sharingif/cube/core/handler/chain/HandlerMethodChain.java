package com.sharingif.cube.core.handler.chain;

import com.sharingif.cube.core.exception.CubeException;

/**
 * HandlerMethod执行责任链
 * 2015年8月1日 下午4:25:07
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface HandlerMethodChain {
	
	/**
	 * HandlerMethod调用前执行
	 * @param handlerMethodContent
	 * @throws CubeException
	 */
	void before(HandlerMethodContent handlerMethodContent) throws CubeException;
	
	/**
	 * HandlerMethod调用后执行
	 * @param handlerMethodContent
	 * @throws CubeException
	 */
	void after(HandlerMethodContent handlerMethodContent) throws CubeException;
	
	/**
	 * HandlerMethod异常时执行
	 * @param handlerMethodContent
	 * @param exception
	 * @return 返回true异常终止，否则异常继续抛出
	 */
	void exception(HandlerMethodContent handlerMethodContent, Exception exception)  throws CubeException;

}
