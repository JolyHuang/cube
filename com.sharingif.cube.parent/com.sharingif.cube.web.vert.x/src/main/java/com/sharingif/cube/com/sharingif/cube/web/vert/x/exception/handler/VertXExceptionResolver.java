package com.sharingif.cube.com.sharingif.cube.web.vert.x.exception.handler;

import com.sharingif.cube.core.exception.handler.AbstractCubeExceptionHandler;
import com.sharingif.cube.core.exception.handler.IExceptionResolver;
import com.sharingif.cube.core.method.DefaultInvocableHandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.exception.handler.WebExceptionContent;

import io.vertx.ext.web.RoutingContext;

/**
 * TODO
 * 2016年12月28日 下午6:08:22
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXExceptionResolver implements IExceptionResolver<RoutingContext, WebExceptionContent, DefaultInvocableHandlerMethod> {
	
	private AbstractCubeExceptionHandler<RoutingContext, WebExceptionContent, DefaultInvocableHandlerMethod> cubeExceptionHandler;
	
	public AbstractCubeExceptionHandler<RoutingContext, WebExceptionContent, DefaultInvocableHandlerMethod> getCubeExceptionHandler() {
		return cubeExceptionHandler;
	}
	public void setCubeExceptionHandler(AbstractCubeExceptionHandler<RoutingContext, WebExceptionContent, DefaultInvocableHandlerMethod> cubeExceptionHandler) {
		this.cubeExceptionHandler = cubeExceptionHandler;
	}

	@Override
	public WebExceptionContent resolverException(RequestInfo<RoutingContext> requestInfo, DefaultInvocableHandlerMethod handler, Exception exception) {
		
		return cubeExceptionHandler.handler(requestInfo, handler, exception);
		
	}

}
