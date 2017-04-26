package com.sharingif.cube.core.method.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;

import com.sharingif.cube.core.method.HandlerMethodContent;

public abstract class AbstractHandlerMethodChain<T extends HandlerMethodContent> extends ApplicationObjectSupport implements HandlerMethodChain<T> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void exception(T handlerMethodContent, Exception exception) {
		
	}
	
}
