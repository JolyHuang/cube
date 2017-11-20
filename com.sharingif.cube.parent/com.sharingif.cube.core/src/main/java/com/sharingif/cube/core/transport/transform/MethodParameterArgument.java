package com.sharingif.cube.core.transport.transform;

import org.springframework.core.MethodParameter;

import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;

/**
 * 包含MethodParameter、RequestContext、DataBinderFactory、connect return
 * 2017年5月12日 上午11:22:30
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MethodParameterArgument<RI,CO> {
	
	private MethodParameter methodParameter;
	private RequestContext<RI> requestContext;
	private DataBinderFactory dataBinderFactory;
	private CO connectReturnValue;
	
	public MethodParameterArgument(MethodParameter methodParameter, RequestContext<RI> requestContext, DataBinderFactory dataBinderFactory, CO connectReturnValue) {
		super();
		this.methodParameter = methodParameter;
		this.requestContext = requestContext;
		this.dataBinderFactory = dataBinderFactory;
		this.connectReturnValue = connectReturnValue;
	}
	
	public MethodParameter getMethodParameter() {
		return methodParameter;
	}
	public RequestContext<RI> getRequestContext() {
		return requestContext;
	}
	public CO getConnectReturnValue() {
		return connectReturnValue;
	}
	public DataBinderFactory getDataBinderFactory() {
		return dataBinderFactory;
	}
	
}
