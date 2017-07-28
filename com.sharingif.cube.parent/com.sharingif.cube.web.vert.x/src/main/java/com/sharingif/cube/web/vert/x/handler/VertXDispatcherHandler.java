package com.sharingif.cube.web.vert.x.handler;

import com.sharingif.cube.communication.handler.AbstractDispatcherHandler;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.web.vert.x.http.VertXHttpRequest;
import com.sharingif.cube.web.vert.x.http.VertXHttpResponse;
import com.sharingif.cube.web.vert.x.request.ExtendedRoutingContext;
import com.sharingif.cube.web.vert.x.request.VertXRequestInfo;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

/**
 * VertXDispatcherHandler
 * 2016年12月18日 下午4:30:57
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXDispatcherHandler extends AbstractDispatcherHandler<ExtendedRoutingContext> {

	@Override
	protected HandlerMethodContent getHandlerMethodContent(ExtendedRoutingContext request) {
		
		HttpServerRequest httpServerRequest = request.getRoutingContext().request();
		
		String mediaType = httpServerRequest.headers().get(HttpHeaders.CONTENT_TYPE);
		String lookupPath = httpServerRequest.path().replace(new StringBuilder("/").append(request.getContextPath()).toString(), "");
		String method = httpServerRequest.rawMethod();
		RoutingContext routingContext = request.getRoutingContext();
		
		VertXRequestInfo requestInfo = new VertXRequestInfo(
				mediaType
				,lookupPath
				,null
				,method
				,new VertXHttpRequest(routingContext.request())
				,new VertXHttpResponse(routingContext.response())
				,request.getRoutingContext()
			);
		
		HandlerMethodContent webHandlerMethodContent = new HandlerMethodContent(
				null
				,null
				,null
				,null
				,requestInfo
		);
		
		return webHandlerMethodContent;
	}

}
