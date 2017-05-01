package com.sharingif.cube.communication.method.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.exception.handler.IExceptionResolver;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.adapter.HandlerAdapter;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.mapping.HandlerMapping;
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
	private List<HandlerMapping<H>> handlerMappings;
	private List<HandlerAdapter<H>> handlerAdapters;
	private IExceptionResolver<RI,ExceptionContent,H> exceptionResolver;
	private List<ViewResolver<RI,ExceptionContent>> viewResolvers;
	private HandlerMethodChain<HandlerMethodContent> handlerMethodChain;
	
	
	public RequestInfoResolver<I, RI> getRequestInfoResolver() {
		return requestInfoResolver;
	}
	public void setRequestInfoResolver(RequestInfoResolver<I, RI> requestInfoResolver) {
		this.requestInfoResolver = requestInfoResolver;
	}
	public List<HandlerMapping<H>> getHandlerMappings() {
		return handlerMappings;
	}
	public void setHandlerMappings(List<HandlerMapping<H>> handlerMappings) {
		this.handlerMappings = handlerMappings;
	}
	public List<HandlerAdapter<H>> getHandlerAdapters() {
		return handlerAdapters;
	}
	public void setHandlerAdapters(List<HandlerAdapter<H>> handlerAdapters) {
		this.handlerAdapters = handlerAdapters;
	}
	public IExceptionResolver<RI, ExceptionContent, H> getExceptionResolver() {
		return exceptionResolver;
	}
	public void setExceptionResolver(IExceptionResolver<RI, ExceptionContent, H> exceptionResolver) {
		this.exceptionResolver = exceptionResolver;
	}
	public List<ViewResolver<RI,ExceptionContent>> getViewResolvers() {
		return viewResolvers;
	}
	public void setViewResolvers(List<ViewResolver<RI,ExceptionContent>> viewResolvers) {
		this.viewResolvers = viewResolvers;
	}
	public HandlerMethodChain<HandlerMethodContent> getHandlerMethodChain() {
		return handlerMethodChain;
	}
	public void setHandlerMethodChain(HandlerMethodChain<HandlerMethodContent> handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}
	
	
	@Override
	public void doDispatch(I request) {
		
		RequestInfo<RI> requestInfo = null;
		H handler = null;
		Object returnValue = null;
		ExceptionContent exceptionContent = null;
		View<RI,ExceptionContent> view = null;
		boolean handlerMethodChainIsEmpty = (null != getHandlerMethodChain());
		HandlerMethodContent handlerMethodContent = null;
		try {
			
			if(handlerMethodChainIsEmpty) {
				handlerMethodContent = getHandlerMethodContent(request);
				getHandlerMethodChain().before(handlerMethodContent);
			}
			
			requestInfo = requestInfoResolver.resolveRequest(request);
			
			handler = getHandler(requestInfo);
			if(handler == null) {
				logger.error("No mapping found for request with URI {}", requestInfo.getLookupPath());
				noHandlerFound(requestInfo);
				return;
			}
			
			HandlerAdapter<H> handlerAdapter = getHandlerAdapter(handler);
			if(handlerAdapter == null) {
				logger.error("No adapter found for handler");
				noAdapterHandlerFound(requestInfo, handler);
				return;
			}
			
			returnValue = handlerAdapter.handle(requestInfo, handler);
			
		} catch (Exception e) {
			logger.error("do dispatch error", e);
			try {
				exceptionContent = exceptionResolver.resolverException(requestInfo, handler, e);
			} catch (Exception e1) {
				logger.error("do exception resolver error", e);
				unknownException(requestInfo, returnValue);
				return;
			}
		}
		
		try {
			view = getView(requestInfo, returnValue, exceptionContent);
			if(view == null) {
				logger.error("No view found for request");
				noViewFound(requestInfo, returnValue);
				return;
			}
		} catch (Exception e) {
			logger.error("do view resolver error", e);
			unknownException(requestInfo, returnValue);
		}
		
		try {
			view.view(requestInfo, returnValue, exceptionContent);
		} catch (Exception e) {
			logger.error("handle view error", e);
			unknownException(requestInfo, returnValue);
		}
		
		try {
			if(handlerMethodChainIsEmpty) {
				getHandlerMethodChain().after(handlerMethodContent);
			}
		} catch (Exception e) {
			logger.error("handler method chain error", e);
		}
	}
	
	/**
	 * Return the HandlerExecutionChain for this request.
	 * <p>Tries all handler mappings in order.
	 * @param request current HTTP request
	 * @return the HandlerExecutionChain, or {@code null} if no handler could be found
	 * @throws CubeException 
	 */
	protected H getHandler(RequestInfo<RI> requestInfo) throws CubeException {
		for (HandlerMapping<H> hm : this.handlerMappings) {
			H handler = hm.getHandler(requestInfo);
			if (handler != null) {
				return handler;
			}
		}
		return null;
	}
	
	protected HandlerAdapter<H> getHandlerAdapter(Object handler) {
		for (HandlerAdapter<H> ha : this.handlerAdapters) {
			if (logger.isTraceEnabled()) {
				logger.trace("Testing handler adapter [" + ha + "]");
			}
			if (ha.supports(handler)) {
				return ha;
			}
		}
		return null;
	}
	
	protected View<RI,ExceptionContent> getView(RequestInfo<RI> requestInfo, Object returnValue, ExceptionContent exceptionContent) {
		for(ViewResolver<RI,ExceptionContent> viewResolver : viewResolvers) {
			View<RI,ExceptionContent> view = viewResolver.resolveView(requestInfo, returnValue, exceptionContent);
			if(view != null){
				return view;
			}
		}
		return null;
	}
	
	protected void noHandlerFound(RequestInfo<RI> requestInfo) {
		unknownException(requestInfo, null);
	}
	
	protected void noAdapterHandlerFound(RequestInfo<RI> requestInfo, H handler) {
		unknownException(requestInfo, null);
	}
	
	protected void noViewFound(RequestInfo<RI> requestInfo, Object returnValue) {
		unknownException(requestInfo, returnValue);
	}
	
	abstract protected void unknownException(RequestInfo<RI> requestInfo, Object returnValue);
	
	protected HandlerMethodContent getHandlerMethodContent(I request) {
		return new HandlerMethodContent(null, null, null, null, null, null, null);
	}
	
}
