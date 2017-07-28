package com.sharingif.cube.web.vert.x.request;

import com.sharingif.cube.core.request.RequestInfoResolver;
import com.sharingif.cube.web.vert.x.http.VertXHttpRequest;
import com.sharingif.cube.web.vert.x.http.VertXHttpResponse;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

/**
 * VertXRequestInfoResolver
 * 2016年12月18日 下午4:52:59
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXRequestInfoResolver implements RequestInfoResolver<ExtendedRoutingContext, VertXRequestInfo> {

	@Override
	public VertXRequestInfo resolveRequest(ExtendedRoutingContext request) {

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
				,new VertXHttpResponse(routingContext.response()));
		
		return requestInfo;
	}

}
