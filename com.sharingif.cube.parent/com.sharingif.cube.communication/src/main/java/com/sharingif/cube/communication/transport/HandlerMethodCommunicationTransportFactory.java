package com.sharingif.cube.communication.transport;

import java.lang.reflect.Method;

/**
 * HandlerMethodCommunicationTransportFactory
 * 2017年5月17日 下午4:04:40
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface HandlerMethodCommunicationTransportFactory<MI, MO, CO, UI, UO> {
	
	AbstractHandlerMethodCommunicationTransport<MI,MO,CO,UI,UO>  createHandlerMethodCommunicationTransport(Object bean, Method method);

}
