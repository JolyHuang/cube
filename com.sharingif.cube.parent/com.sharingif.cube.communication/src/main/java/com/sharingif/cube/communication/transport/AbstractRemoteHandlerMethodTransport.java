package com.sharingif.cube.communication.transport;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;

/**
 * AbstractRemoteHandlerMethodTransport
 * 2017年1月16日 下午4:30:32
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractRemoteHandlerMethodTransport<I,MI,MO,UI,UO,CI,CO> extends HandlerMethod implements Transport<I,MI,MO,UI,UO,CI,CO> {

	private DataBinderFactory dataBinderFactory;
	private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	private HandlerMethodChain<HandlerMethodContent> handlerMethodChain;
	
	public void setDataBinderFactory(DataBinderFactory dataBinderFactory) {
		this.dataBinderFactory = dataBinderFactory;
	}
	public DataBinderFactory getDataBinderFactory() {
		return dataBinderFactory;
	}
	public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}
	public ParameterNameDiscoverer getParameterNameDiscoverer() {
		return parameterNameDiscoverer;
	}
	public HandlerMethodChain<HandlerMethodContent> getHandlerMethodChain() {
		return handlerMethodChain;
	}
	public void setHandlerMethodChain(HandlerMethodChain<HandlerMethodContent> handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}
	
	protected AbstractRemoteHandlerMethodTransport(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}


}
