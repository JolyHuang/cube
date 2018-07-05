package com.sharingif.cube.communication.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.exception.NoViewFoundException;
import com.sharingif.cube.communication.view.exception.ViewException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.request.RequestContextResolver;

/**
 * 请求调度处理器
 * 2016年4月24日 下午9:04:10
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractDispatcherHandler<I> implements DispatcherHandler<I> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("rawtypes")
	private RequestContextResolver requestContextResolver;
	private MultiHandlerMapping multiHandlerMapping;
	private MultiHandlerMethodAdapter multiHandlerMethodAdapter;
	@SuppressWarnings("rawtypes")
	private MultiCubeExceptionHandler multiCubeExceptionHandler;
	private MultiViewResolver multiViewResolver;
	private HandlerMethodChain handlerMethodChain;
	
	
	@SuppressWarnings("rawtypes")
	public RequestContextResolver getRequestContextResolver() {
		return requestContextResolver;
	}
	@SuppressWarnings("rawtypes")
	public void setRequestContextResolver(RequestContextResolver requestContextResolver) {
		this.requestContextResolver = requestContextResolver;
	}
	public MultiHandlerMapping getMultiHandlerMapping() {
		return multiHandlerMapping;
	}
	public void setMultiHandlerMapping(MultiHandlerMapping multiHandlerMapping) {
		this.multiHandlerMapping = multiHandlerMapping;
	}
	public MultiHandlerMethodAdapter getMultiHandlerMethodAdapter() {
		return multiHandlerMethodAdapter;
	}
	public void setMultiHandlerMethodAdapter(MultiHandlerMethodAdapter multiHandlerMethodAdapter) {
		this.multiHandlerMethodAdapter = multiHandlerMethodAdapter;
	}

	@SuppressWarnings("rawtypes")
	public MultiCubeExceptionHandler getMultiCubeExceptionHandler() {
		return multiCubeExceptionHandler;
	}
	@SuppressWarnings("rawtypes")
	public void setMultiCubeExceptionHandler(MultiCubeExceptionHandler multiCubeExceptionHandler) {
		this.multiCubeExceptionHandler = multiCubeExceptionHandler;
	}
	public MultiViewResolver getMultiViewResolver() {
		return multiViewResolver;
	}
	public void setMultiViewResolver(MultiViewResolver multiViewResolver) {
		this.multiViewResolver = multiViewResolver;
	}
	public HandlerMethodChain getHandlerMethodChain() {
		return handlerMethodChain;
	}
	public void setHandlerMethodChain(HandlerMethodChain handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}

	@Override
	public void doDispatch(I request) {
		boolean handlerMethodChainIsNotEmpty = (null != getHandlerMethodChain());
		HandlerMethodContent handlerMethodContent = null;

		try {

			if(handlerMethodChainIsNotEmpty) {
				handlerMethodContent = getHandlerMethodContent(request);
				getHandlerMethodChain().before(handlerMethodContent);
			}

			doDispatchInternal(request);
		} catch (Exception exception) {
			try {
				getHandlerMethodChain().exception(handlerMethodContent, exception);
			} catch (Exception e) {
				logger.error("handle method exception chain error", e);
			}
		}


		try {
			if(handlerMethodChainIsNotEmpty) {
				getHandlerMethodChain().after(handlerMethodContent);
			}
		} catch (Exception e) {
			logger.error("handle method after chain error", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void doDispatchInternal(I request) {

		RequestContext<Object> requestContext = null;
		Object handler = null;
		Object returnValue = null;
		ExceptionContent exceptionContent = null;
		try {

			requestContext = getRequestContextResolver().resolveRequest(request);

			handler = getMultiHandlerMapping().getHandler(requestContext);

			returnValue = getMultiHandlerMethodAdapter().handle(requestContext,handler);

		} catch (Exception e) {
			logger.error("do dispatch error", e);
			exceptionContent = multiCubeExceptionHandler.handler(requestContext, handler, e);
		}

		handlerView(requestContext,returnValue,exceptionContent);

	}

	protected void handlerView(RequestContext<Object> requestContext, Object returnValue, ExceptionContent exceptionContent) {
		try {
			View view = getMultiViewResolver().resolveView(requestContext, returnValue, exceptionContent);
		
			view.view(requestContext, returnValue, exceptionContent);
		} catch (NoViewFoundException exception) {
			throw exception;
		} catch (Exception exception) {
			logger.error("handle view error", exception);
			throw new ViewException(exception);
		}
	}
	
	protected HandlerMethodContent getHandlerMethodContent(I request) {
		return new HandlerMethodContent(
				null,
				null,
				null,
				null,
				new RequestContext<I>(null,null,null,null,request));
	}
	
}
