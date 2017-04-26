package com.sharingif.cube.com.sharingif.cube.web.vert.x.request;

import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.request.RequestInfoResolver;

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
public class VertXRequestInfoResolver implements RequestInfoResolver<ExtendedRoutingContext, RoutingContext> {

	@Override
	public RequestInfo<RoutingContext> resolveRequest(ExtendedRoutingContext request) {

		HttpServerRequest httpServerRequest = request.getRoutingContext().request();
		
		String mediaType = httpServerRequest.headers().get(HttpHeaders.CONTENT_TYPE);
		String lookupPath = httpServerRequest.path().replace(new StringBuilder("/").append(request.getContextPath()).toString(), "");
		String method = httpServerRequest.rawMethod();
		RoutingContext routingContext = request.getRoutingContext();
		
		RequestInfo<RoutingContext> requestInfo = new RequestInfo<RoutingContext>(mediaType,lookupPath,null,method,routingContext);
		
		return requestInfo;
	}

}
