package com.sharingif.cube.web.vert.x.exception.handler;

import com.sharingif.cube.core.exception.handler.AbstractCubeExceptionHandler;
import com.sharingif.cube.core.exception.handler.IExceptionResolver;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.exception.handler.WebExceptionContent;
import io.vertx.ext.web.RoutingContext;

/**
 * VertXExceptionResolver
 * 2016年12月28日 下午6:08:22
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXExceptionResolver implements IExceptionResolver<RoutingContext, WebExceptionContent, HandlerMethod> {

	private AbstractCubeExceptionHandler<RoutingContext, WebExceptionContent, HandlerMethod> cubeExceptionHandler;
	
	public AbstractCubeExceptionHandler<RoutingContext, WebExceptionContent, HandlerMethod> getCubeExceptionHandler() {
		return cubeExceptionHandler;
	}
	public void setCubeExceptionHandler(AbstractCubeExceptionHandler<RoutingContext, WebExceptionContent, HandlerMethod> cubeExceptionHandler) {
		this.cubeExceptionHandler = cubeExceptionHandler;
	}

	@Override
	public WebExceptionContent resolverException(RequestInfo<RoutingContext> requestInfo, HandlerMethod handler, Exception exception) {
		
		return cubeExceptionHandler.handler(requestInfo, handler, exception);
		
	}

}
