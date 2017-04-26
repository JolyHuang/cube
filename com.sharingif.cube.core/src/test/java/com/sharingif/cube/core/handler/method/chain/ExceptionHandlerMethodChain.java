package com.sharingif.cube.core.handler.method.chain;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.method.HandlerMethodContent;
import com.sharingif.cube.core.method.chain.AbstractHandlerMethodChain;

/**
 * TODO description
 * 2015年8月8日 下午1:47:20
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExceptionHandlerMethodChain extends AbstractHandlerMethodChain<HandlerMethodContent> {

	@Override
	public void before(HandlerMethodContent handlerMethodContent) throws CubeException {
	}

	@Override
	public void after(HandlerMethodContent handlerMethodContent) throws CubeException {
		int i=0;
		
		i = 100/i;
	}

}
