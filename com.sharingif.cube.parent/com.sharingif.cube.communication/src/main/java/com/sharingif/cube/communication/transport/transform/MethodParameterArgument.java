package com.sharingif.cube.communication.transport.transform;

import org.springframework.core.MethodParameter;

import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * 包含MethodParameter、RequestInfo、DataBinderFactory、connect return
 * 2017年5月12日 上午11:22:30
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MethodParameterArgument<RI,CO> {
	
	private MethodParameter methodParameter;
	private RequestInfo<RI> requestInfo;
	private DataBinderFactory dataBinderFactory;
	private CO connectReturnValue;
	
	public MethodParameterArgument(MethodParameter methodParameter, RequestInfo<RI> requestInfo, DataBinderFactory dataBinderFactory, CO connectReturnValue) {
		super();
		this.methodParameter = methodParameter;
		this.requestInfo = requestInfo;
		this.dataBinderFactory = dataBinderFactory;
		this.connectReturnValue = connectReturnValue;
	}
	
	public MethodParameter getMethodParameter() {
		return methodParameter;
	}
	public RequestInfo<RI> getRequestInfo() {
		return requestInfo;
	}
	public CO getConnectReturnValue() {
		return connectReturnValue;
	}
	public DataBinderFactory getDataBinderFactory() {
		return dataBinderFactory;
	}
	
}
