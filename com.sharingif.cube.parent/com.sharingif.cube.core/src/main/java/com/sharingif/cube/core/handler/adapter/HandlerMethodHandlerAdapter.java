package com.sharingif.cube.core.handler.adapter;

import java.util.List;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.DefaultInvocableHandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.handler.bind.support.DefaultDataBinderFactory;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * An {@link AbstractHandlerMethodAdapter} that supports {@link HandlerMethod}s
 * with the signature -- method argument and return types, defined in
 * {@code @RequestMapping}.
 *
 * <p>Support for custom argument and return value types can be added via
 * {@link #setCustomArgumentResolvers} and {@link #setCustomReturnValueHandlers}.
 * Or alternatively to re-configure all argument and return value types use
 * {@link #setArgumentResolvers} and {@link #setReturnValueHandlers(List)}.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 * @see HandlerMethodArgumentResolver
 * @see HandlerMethodReturnValueHandler
 */
public class HandlerMethodHandlerAdapter extends AbstractHandlerMethodAdapter {
	
	private HandlerMethodArgumentResolverComposite argumentResolvers;
	
	private HandlerMethodReturnValueHandlerComposite returnValueHandlers;
	
	private BindingInitializer bindingInitializer;
	
	private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	
	private HandlerMethodChain<HandlerMethodContent> handlerMethodChain;
	
	/**
	 * Configure the complete list of supported argument types thus overriding
	 * the resolvers that would otherwise be configured by default.
	 */
	public void setArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		if (argumentResolvers == null) {
			this.argumentResolvers = null;
		}
		else {
			this.argumentResolvers = new HandlerMethodArgumentResolverComposite();
			this.argumentResolvers.addResolvers(argumentResolvers);
		}
	}
	
	/**
	 * Return the configured argument resolvers, or possibly {@code null} if
	 * not initialized yet via {@link #afterPropertiesSet()}.
	 */
	public List<HandlerMethodArgumentResolver> getArgumentResolvers() {
		return (this.argumentResolvers != null) ? this.argumentResolvers.getResolvers() : null;
	}
	
	/**
	 * Configure the complete list of supported return value types thus
	 * overriding handlers that would otherwise be configured by default.
	 */
	public void setReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		if (returnValueHandlers == null) {
			this.returnValueHandlers = null;
		}
		else {
			this.returnValueHandlers = new HandlerMethodReturnValueHandlerComposite();
			this.returnValueHandlers.addHandlers(returnValueHandlers);
		}
	}

	/**
	 * Return the configured handlers, or possibly {@code null} if not
	 * initialized yet via {@link #afterPropertiesSet()}.
	 */
	public List<HandlerMethodReturnValueHandler> getReturnValueHandlers() {
		return this.returnValueHandlers.getHandlers();
	}
	
	public BindingInitializer getBindingInitializer() {
		return bindingInitializer;
	}

	public void setBindingInitializer(BindingInitializer bindingInitializer) {
		this.bindingInitializer = bindingInitializer;
	}
	
	/**
	 * Set the ParameterNameDiscoverer to use for resolving method parameter names if needed
	 * (e.g. for default attribute names).
	 * <p>Default is a {@link org.springframework.core.DefaultParameterNameDiscoverer}.
	 */
	public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}

	public HandlerMethodChain<HandlerMethodContent> getHandlerMethodChain() {
		return handlerMethodChain;
	}

	public void setHandlerMethodChain(HandlerMethodChain<HandlerMethodContent> handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}
	
	@Override
	protected boolean supportsInternal(HandlerMethod handlerMethod) {
		return true;
	}

	@Override
	protected Object handleInternal(RequestInfo<?> request, HandlerMethod handlerMethod) throws CubeException {
		return invokeHandleMethod(request, handlerMethod);
	}
	
	/**
	 * Invoke the {@link RequestMapping} handler method preparing a {@link ModelAndView}
	 * if view resolution is required.
	 */
	protected Object invokeHandleMethod(RequestInfo<?> request, HandlerMethod handlerMethod) throws CubeException {
		DataBinderFactory dataBinderFactory = getDataBinderFactory(handlerMethod);
		
		return getHandlerMethod(handlerMethod, dataBinderFactory).invokeAndHandle(request);
	}
	
	protected DataBinderFactory getDataBinderFactory(HandlerMethod handlerMethod) {
		return new DefaultDataBinderFactory(getBindingInitializer());
	}
	
	private DefaultInvocableHandlerMethod getHandlerMethod(HandlerMethod handlerMethod, DataBinderFactory dataBinderFactory) {
		
		DefaultInvocableHandlerMethod defaultInvocableHandlerMethod = new DefaultInvocableHandlerMethod(handlerMethod);
		defaultInvocableHandlerMethod.setHandlerMethodArgumentResolvers(this.argumentResolvers);
		defaultInvocableHandlerMethod.setHandlerMethodReturnValueHandlers(this.returnValueHandlers);
		defaultInvocableHandlerMethod.setDataBinderFactory(dataBinderFactory);
		defaultInvocableHandlerMethod.setParameterNameDiscoverer(this.parameterNameDiscoverer);
		defaultInvocableHandlerMethod.setHandlerMethodChain(handlerMethodChain);
		
		return defaultInvocableHandlerMethod;
	}
	
}
