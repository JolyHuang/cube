package com.sharingif.cube.communication.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.exception.NoViewFoundException;
import com.sharingif.cube.communication.view.exception.ViewException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.exception.handler.IExceptionResolver;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.request.RequestInfoResolver;

/**
 * 请求调度处理器
 * 2016年4月24日 下午9:04:10
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractDispatcherHandler<I,RI,H extends HandlerMethod> implements DispatcherHandler<I> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private RequestInfoResolver<I,RI> requestInfoResolver;
	private MultiHandlerMapping<H> multiHandlerMapping;
	private MultiHandlerMethodAdapter<H> multiHandlerMethodAdapter;
	private IExceptionResolver<RI,H> exceptionResolver;
	private MultiViewResolver<RI> multiViewResolver;
	private HandlerMethodChain<HandlerMethodContent> handlerMethodChain;
	
	public RequestInfoResolver<I, RI> getRequestInfoResolver() {
		return requestInfoResolver;
	}
	public void setRequestInfoResolver(RequestInfoResolver<I, RI> requestInfoResolver) {
		this.requestInfoResolver = requestInfoResolver;
	}
	public MultiHandlerMapping<H> getMultiHandlerMapping() {
		return multiHandlerMapping;
	}
	public void setMultiHandlerMapping(MultiHandlerMapping<H> multiHandlerMapping) {
		this.multiHandlerMapping = multiHandlerMapping;
	}
	public MultiHandlerMethodAdapter<H> getMultiHandlerMethodAdapter() {
		return multiHandlerMethodAdapter;
	}
	public void setMultiHandlerMethodAdapter(MultiHandlerMethodAdapter<H> multiHandlerMethodAdapter) {
		this.multiHandlerMethodAdapter = multiHandlerMethodAdapter;
	}
	public IExceptionResolver<RI, H> getExceptionResolver() {
		return exceptionResolver;
	}
	public void setExceptionResolver(IExceptionResolver<RI,H> exceptionResolver) {
		this.exceptionResolver = exceptionResolver;
	}
	public MultiViewResolver<RI> getMultiViewResolver() {
		return multiViewResolver;
	}
	public void setMultiViewResolver(MultiViewResolver<RI> multiViewResolver) {
		this.multiViewResolver = multiViewResolver;
	}
	public HandlerMethodChain<HandlerMethodContent> getHandlerMethodChain() {
		return handlerMethodChain;
	}
	public void setHandlerMethodChain(HandlerMethodChain<HandlerMethodContent> handlerMethodChain) {
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
	
	protected void doDispatchInternal(I request) {

		RequestInfo<RI> requestInfo = null;
		H handler = null;
		Object returnValue = null;
		ExceptionContent exceptionContent = null;
		try {

			requestInfo = getRequestInfoResolver().resolveRequest(request);

			handler = getMultiHandlerMapping().getHandler(requestInfo);

			returnValue = getMultiHandlerMethodAdapter().handle(requestInfo,handler);

		} catch (Exception e) {
			logger.error("do dispatch error", e);
			exceptionContent = exceptionResolver.resolverException(requestInfo, handler, e);
		}

		handlerView(requestInfo,returnValue,exceptionContent);

	}

	protected void handlerView(RequestInfo<RI> requestInfo, Object returnValue, ExceptionContent exceptionContent) {
		try {
			View<RI> view = getMultiViewResolver().resolveView(requestInfo, returnValue, exceptionContent);
		
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
				null,
				null,
				new RequestInfo<I>(null,null,null,null,request));
	}
	
}
