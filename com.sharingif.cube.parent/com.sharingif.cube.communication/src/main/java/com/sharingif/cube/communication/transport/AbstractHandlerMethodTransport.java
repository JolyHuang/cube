package com.sharingif.cube.communication.transport;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.transport.Transport;
import com.sharingif.cube.core.transport.transform.Transform;

/**
 * AbstractHandlerMethodTransport
 * 2017年1月16日 下午4:30:32
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHandlerMethodTransport<MI,MO,UI,UO> extends HandlerMethod implements Transport<MI,MO,UI,UO> {

	private Transform<MI,MO,UI,UO> transform;
	
	private DataBinderFactory dataBinderFactory;
	private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	private HandlerMethodChain handlerMethodChain;
	
	public void setTransform(Transform<MI,MO,UI,UO> transform) {
		this.transform = transform;
	}
	public Transform<MI, MO, UI, UO> getTransform() {
		return transform;
	}

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
	public HandlerMethodChain getHandlerMethodChain() {
		return handlerMethodChain;
	}
	public void setHandlerMethodChain(HandlerMethodChain handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}
	
	protected AbstractHandlerMethodTransport(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}


}
