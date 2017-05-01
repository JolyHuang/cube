package com.sharingif.cube.com.sharingif.cube.web.vert.x.method.handler;

import org.springframework.http.MediaType;

import com.sharingif.cube.com.sharingif.cube.web.vert.x.http.VertXHttpRequest;
import com.sharingif.cube.com.sharingif.cube.web.vert.x.http.VertXHttpResponse;
import com.sharingif.cube.com.sharingif.cube.web.vert.x.request.ExtendedRoutingContext;
import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.communication.method.handler.AbstractDispatcherHandler;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.request.RequestInfo;

import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

/**
 * VertXDispatcherHandler
 * 2016年12月18日 下午4:30:57
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXDispatcherHandler extends AbstractDispatcherHandler<ExtendedRoutingContext,RoutingContext,HandlerMethod> {

	@Override
	protected void unknownException(RequestInfo<RoutingContext> requestInfo, Object returnValue) {
		requestInfo.getRequest().response()
			.putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
			.setStatusCode(404)
			.end();
	}

	@Override
	protected HandlerMethodContent getHandlerMethodContent(ExtendedRoutingContext request) {
		HttpHandlerMethodContent webHandlerMethodContent = new HttpHandlerMethodContent(
				null
				,null
				,null
				,null
				,null
				,null
				,null
				,new VertXHttpRequest(request.getRoutingContext().request())
				, new VertXHttpResponse(request.getRoutingContext().request().response()));
		
		return webHandlerMethodContent;
	}

}
