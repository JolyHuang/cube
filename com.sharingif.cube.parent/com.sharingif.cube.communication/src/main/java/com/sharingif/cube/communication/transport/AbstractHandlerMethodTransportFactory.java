package com.sharingif.cube.communication.transport;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.transport.transform.Transform;

/**
 * AbstractHandlerMethodTransportFactory
 * 2017年1月9日 下午8:56:38
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHandlerMethodTransportFactory<MI,MO,CI,CO,UI,UO> implements HandlerMethodCommunicationTransportFactory<MI,MO,CI,CO,UI,UO>  {
	
	private Transform<MI,MO,UI,UO> transform;
	
	private DataBinderFactory dataBinderFactory;
	private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	private HandlerMethodChain handlerMethodChain;
	
	public Transform<MI, MO, UI, UO> getTransform() {
		return transform;
	}
	public void setTransform(Transform<MI, MO, UI, UO> transform) {
		this.transform = transform;
	}
	
	public DataBinderFactory getDataBinderFactory() {
		return dataBinderFactory;
	}
	public void setDataBinderFactory(DataBinderFactory dataBinderFactory) {
		this.dataBinderFactory = dataBinderFactory;
	}
	public ParameterNameDiscoverer getParameterNameDiscoverer() {
		return parameterNameDiscoverer;
	}
	public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}
	public HandlerMethodChain getHandlerMethodChain() {
		return handlerMethodChain;
	}
	public void setHandlerMethodChain(HandlerMethodChain handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}
	
}
