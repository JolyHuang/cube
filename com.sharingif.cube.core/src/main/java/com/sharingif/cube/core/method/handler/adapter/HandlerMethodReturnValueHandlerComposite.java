package com.sharingif.cube.core.method.handler.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.method.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * Handles method return values by delegating to a list of registered
 * {@link HandlerMethodReturnValueHandler}s. Previously resolved return types
 * are cached for faster lookups.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 */
public class HandlerMethodReturnValueHandlerComposite implements
		HandlerMethodReturnValueHandler {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<HandlerMethodReturnValueHandler>();

	/**
	 * Return a read-only list with the registered handlers, or an empty list.
	 */
	public List<HandlerMethodReturnValueHandler> getHandlers() {
		return Collections.unmodifiableList(this.returnValueHandlers);
	}

	/**
	 * Whether the given {@linkplain MethodParameter method return type} is
	 * supported by any registered {@link HandlerMethodReturnValueHandler}.
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return getReturnValueHandler(returnType) != null;
	}

	/**
	 * Iterate over registered {@link HandlerMethodReturnValueHandler}s and
	 * invoke the one that supports it.
	 * 
	 * @throws IllegalStateException
	 *             if no suitable {@link HandlerMethodReturnValueHandler} is
	 *             found.
	 */
	@Override
	public Object handleReturnValue(RequestInfo<?> requestInfo, Object returnValue, MethodParameter returnType, DataBinderFactory dataBinderFactory) throws CubeException {

		HandlerMethodReturnValueHandler handler = getReturnValueHandler(returnType);
		Assert.notNull(handler, "Unknown return value type ["
				+ returnType.getParameterType().getName() + "]");
		return handler.handleReturnValue(requestInfo, returnValue, returnType, dataBinderFactory);
	}

	/**
	 * Find a registered {@link HandlerMethodReturnValueHandler} that supports
	 * the given return type.
	 */
	private HandlerMethodReturnValueHandler getReturnValueHandler(
			MethodParameter returnType) {
		for (HandlerMethodReturnValueHandler returnValueHandler : returnValueHandlers) {
			if (logger.isTraceEnabled()) {
				logger.trace("Testing if return value handler ["
						+ returnValueHandler + "] supports ["
						+ returnType.getGenericParameterType() + "]");
			}
			if (returnValueHandler.supportsReturnType(returnType)) {
				return returnValueHandler;
			}
		}
		return null;
	}

	/**
	 * Add the given {@link HandlerMethodReturnValueHandler}.
	 */
	public HandlerMethodReturnValueHandlerComposite addHandler(
			HandlerMethodReturnValueHandler handler) {
		returnValueHandlers.add(handler);
		return this;
	}

	/**
	 * Add the given {@link HandlerMethodReturnValueHandler}s.
	 */
	public HandlerMethodReturnValueHandlerComposite addHandlers(
			List<? extends HandlerMethodReturnValueHandler> handlers) {
		if (handlers != null) {
			for (HandlerMethodReturnValueHandler handler : handlers) {
				this.returnValueHandlers.add(handler);
			}
		}
		return this;
	}

}
