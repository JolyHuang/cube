package com.sharingif.cube.core.handler.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;

import com.sharingif.cube.core.exception.CubeException;

public abstract class AbstractHandlerMethodChain extends ApplicationObjectSupport implements HandlerMethodChain {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void exception(HandlerMethodContent handlerMethodContent, Exception exception) throws CubeException {
	}
	
}
