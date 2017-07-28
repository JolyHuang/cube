package com.sharingif.cube.communication.handler;

import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.exception.NoViewFoundException;
import com.sharingif.cube.communication.view.exception.ViewException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.exception.handler.IExceptionResolver;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.request.RequestInfoResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private RequestInfoResolver requestInfoResolver;
	private MultiHandlerMapping multiHandlerMapping;
	private MultiHandlerMethodAdapter multiHandlerMethodAdapter;
	private IExceptionResolver<Object,Object> exceptionResolver;
	private MultiViewResolver multiViewResolver;
	private HandlerMethodChain handlerMethodChain;
	
	@SuppressWarnings("rawtypes")
	public RequestInfoResolver getRequestInfoResolver() {
		return requestInfoResolver;
	}
	@SuppressWarnings("rawtypes")
	public void setRequestInfoResolver(RequestInfoResolver requestInfoResolver) {
		this.requestInfoResolver = requestInfoResolver;
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
	public IExceptionResolver getExceptionResolver() {
		return exceptionResolver;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setExceptionResolver(IExceptionResolver exceptionResolver) {
		this.exceptionResolver = exceptionResolver;
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

		RequestInfo<Object> requestInfo = null;
		Object handler = null;
		Object returnValue = null;
		ExceptionContent exceptionContent = null;
		try {

			requestInfo = getRequestInfoResolver().resolveRequest(request);

			handler = getMultiHandlerMapping().getHandler(requestInfo);

			returnValue = getMultiHandlerMethodAdapter().handle(requestInfo,handler);

		} catch (Exception e) {
			logger.error("do dispatch error", e);
			exceptionContent = getExceptionResolver().resolverException(requestInfo, handler, e);
		}

		handlerView(requestInfo,returnValue,exceptionContent);

	}

	protected void handlerView(RequestInfo<Object> requestInfo, Object returnValue, ExceptionContent exceptionContent) {
		try {
			View<Object> view = getMultiViewResolver().resolveView(requestInfo, returnValue, exceptionContent);
		
			view.view(requestInfo, returnValue, exceptionContent);
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
				new RequestInfo<I>(null,null,null,null,request));
	}
	
}
