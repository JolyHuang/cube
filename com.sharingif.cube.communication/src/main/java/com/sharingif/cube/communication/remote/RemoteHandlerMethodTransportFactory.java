package com.sharingif.cube.communication.remote;

import java.lang.reflect.Method;

import com.sharingif.cube.communication.transport.AbstractRemoteHandlerMethodTransport;

/**
 * RemoteHandlerMethodTransportFactory
 * 2017年1月9日 下午8:56:38
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface RemoteHandlerMethodTransportFactory<I,MI,MO,UI,UO,CI,CO> {
	
	AbstractRemoteHandlerMethodTransport<I,MI,MO,UI,UO,CI,CO>  createRemoteHandlerMethodTransport(Object bean, Method method);

}
