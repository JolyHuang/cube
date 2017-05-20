package com.sharingif.cube.web.vert.x.handler;

import org.springframework.http.MediaType;

import com.sharingif.cube.communication.handler.AbstractDispatcherHandler;
import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.vert.x.http.VertXHttpRequest;
import com.sharingif.cube.web.vert.x.http.VertXHttpResponse;
import com.sharingif.cube.web.vert.x.request.ExtendedRoutingContext;

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
	protected HandlerMethodContent getHandlerMethodContent(ExtendedRoutingContext request) {
		HttpHandlerMethodContent webHandlerMethodContent = new HttpHandlerMethodContent(
				null
				,null
				,null
				,null
				,null
				,null
				,new RequestInfo<RoutingContext>(null,null,null,null,request.getRoutingContext())
				,new VertXHttpRequest(request.getRoutingContext().request())
				,new VertXHttpResponse(request.getRoutingContext().request().response())
		);
		
		return webHandlerMethodContent;
	}

}
