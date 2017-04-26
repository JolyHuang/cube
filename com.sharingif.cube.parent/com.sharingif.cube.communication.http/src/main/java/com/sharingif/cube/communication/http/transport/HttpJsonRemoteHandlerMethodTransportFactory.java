package com.sharingif.cube.communication.http.transport;

import java.lang.reflect.Method;
import java.util.Map;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.remote.RemoteHandlerMethodTransportFactory;
import com.sharingif.cube.communication.transport.Connection;
import com.sharingif.cube.communication.transport.transform.Transform;
import com.sharingif.cube.core.method.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * HttpJsonRemoteHandlerMethodTransportFactory
 * 2017年1月9日 下午9:01:36
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpJsonRemoteHandlerMethodTransportFactory implements RemoteHandlerMethodTransportFactory<RequestInfo<Object[]>,Object[],String,String,JsonModel<Map<String, Object>>,RequestInfo<String>,String> {
	
	private Connection<RequestInfo<String>,String> connection;
	
	private Transform<Object[],String, String, JsonModel<Map<String, Object>>> transform;

	public void setConnection(Connection<RequestInfo<String>,String> connection) {
		this.connection = connection;
	}
	
	public void setTransform(Transform<Object[], String, String, JsonModel<Map<String, Object>>> transform) {
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
