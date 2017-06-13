package com.sharingif.cube.communication.transport;

import java.lang.reflect.Method;

import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.transport.transform.MethodParameterArgument;
import com.sharingif.cube.core.handler.HandlerMethod;

/**
 * 定义生成MethodTransport
 * 2017年5月17日 下午2:06:38
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ProxyInterfaceHandlerMethodCommunicationTransportFactory<MO,CO,UO> extends AbstractHandlerMethodCommunicationTransportFactory<Object[],MO,RequestInfo<MO>,CO,MethodParameterArgument<Object[],CO>,UO> {

	@Override
	public ProxyInterfaceHandlerMethodCommunicationTransport<MO,CO,UO> createHandlerMethodCommunicationTransport(Object bean, Method method) {
		ProxyInterfaceHandlerMethodCommunicationTransport<MO,CO,UO> proxyInterfaceHandlerMethodCommunicationTransport = new ProxyInterfaceHandlerMethodCommunicationTransport<MO,CO,UO>(new HandlerMethod(bean, method));
		proxyInterfaceHandlerMethodCommunicationTransport.setConnection(getConnection());
		proxyInterfaceHandlerMethodCommunicationTransport.setTransform(getTransform());
		proxyInterfaceHandlerMethodCommunicationTransport.setBusinessCommunicationExceptionHandler(getBusinessCommunicationExceptionHandler());
		proxyInterfaceHandlerMethodCommunicationTransport.setDataBinderFactory(getDataBinderFactory());
		proxyInterfaceHandlerMethodCommunicationTransport.setHandlerMethodChain(getHandlerMethodChain());
		
		return proxyInterfaceHandlerMethodCommunicationTransport;
	}

	
}
