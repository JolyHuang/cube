package com.sharingif.cube.core.handler.chain;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.request.RequestLocalContextHolder;

/**
 * 处理一次请求系统自带属性
 * 2015年8月14日 上午12:15:07
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class RequestLocalContextHolderChain extends AbstractHandlerMethodChain<HandlerMethodContent> {

	@Override
	public void before(HandlerMethodContent handlerMethodContent)
			throws CubeException {
		RequestLocalContextHolder.init();
	}

	@Override
	public void after(HandlerMethodContent handlerMethodContent)
			throws CubeException {
		RequestLocalContextHolder.clearContext();
	}

	@Override
	public void exception(HandlerMethodContent handlerMethodContent,
			Exception exception) {
		RequestLocalContextHolder.clearContext();
		super.exception(handlerMethodContent, exception);
	}
	
	

}
