package com.sharingif.cube.communication.http.transport;

import java.lang.reflect.Method;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.remote.RemoteHandlerMethodTransportFactory;
import com.sharingif.cube.communication.transport.Connection;
import com.sharingif.cube.communication.transport.transform.MethodParameterArgument;
import com.sharingif.cube.communication.transport.transform.Transform;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * HttpJsonRemoteHandlerMethodTransportFactory
 * 2017年1月9日 下午9:01:36
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpJsonRemoteHandlerMethodTransportFactory implements RemoteHandlerMethodTransportFactory<RequestInfo<Object[]>,Object[],String,MethodParameterArgument<Object[],String>,JsonModel<Object>,RequestInfo<String>,String> {
	
	private Connection<RequestInfo<String>,String> connection;
	
	private Transform<Object[],String, MethodParameterArgument<Object[],String>, JsonModel<Object>> transform;

	public void setConnection(Connection<RequestInfo<String>,String> connection) {
		this.connection = connection;
	}
	
	public void setTransform(Transform<Object[], String, MethodParameterArgument<Object[],String>, JsonModel<Object>> transform) {
		this.transform = transform;
	}


	@Override
	public HttpJsonRemoteHandlerMethodTransport createRemoteHandlerMethodTransport(Object bean, Method method) {
		HttpJsonRemoteHandlerMethodTransport httpJsonRemoteHandlerMethodTransport = new HttpJsonRemoteHandlerMethodTransport(new HandlerMethod(bean, method));
		httpJsonRemoteHandlerMethodTransport.setConnection(connection);
		httpJsonRemoteHandlerMethodTransport.setTransform(transform);
		
		return httpJsonRemoteHandlerMethodTransport;
	}

}
