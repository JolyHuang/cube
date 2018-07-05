package com.sharingif.cube.core.handler.adapter;

import java.util.List;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.DefaultInvocableHandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.handler.bind.support.DefaultDataBinderFactory;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.request.RequestContext;

public class HandlerMethodHandlerAdapter extends AbstractHandlerMethodAdapter {
	
	private HandlerMethodArgumentResolverComposite argumentResolvers;
	
	private HandlerMethodReturnValueHandlerComposite returnValueHandlers;
	
	private BindingInitializer bindingInitializer;
	
	private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	
	private HandlerMethodChain handlerMethodChain;
	
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

	public HandlerMethodChain getHandlerMethodChain() {
		return handlerMethodChain;
	}

	public void setHandlerMethodChain(HandlerMethodChain handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}
	
	@Override
	protected boolean supportsInternal(HandlerMethod handlerMethod) {
		return true;
	}

	@Override
	protected Object handleInternal(RequestContext<?> request, HandlerMethod handlerMethod) throws CubeException {
		return invokeHandleMethod(request, handlerMethod);
	}
	
	protected Object invokeHandleMethod(RequestContext<?> request, HandlerMethod handlerMethod) throws CubeException {
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
