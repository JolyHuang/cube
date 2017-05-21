package com.sharingif.cube.web.vert.x.exception.handler;

import com.sharingif.cube.core.exception.handler.AbstractCubeExceptionHandler;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.exception.handler.IExceptionResolver;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;
import io.vertx.ext.web.RoutingContext;

/**
 * VertXExceptionResolver
 * 2016年12月28日 下午6:08:22
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXExceptionResolver implements IExceptionResolver<RoutingContext, HandlerMethod> {

	private AbstractCubeExceptionHandler<RoutingContext, HandlerMethod> cubeExceptionHandler;
	
	public AbstractCubeExceptionHandler<RoutingContext, HandlerMethod> getCubeExceptionHandler() {
		return cubeExceptionHandler;
	}
	public void setCubeExceptionHandler(AbstractCubeExceptionHandler<RoutingContext, HandlerMethod> cubeExceptionHandler) {
		this.cubeExceptionHandler = cubeExceptionHandler;
	}

	@Override
	public ExceptionContent resolverException(RequestInfo<RoutingContext> requestInfo, HandlerMethod handler, Exception exception) {
		
		return cubeExceptionHandler.handler(requestInfo, handler, exception);
		
	}

}
