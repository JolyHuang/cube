package com.sharingif.cube.web.vert.x.request;

import java.util.Locale;

import com.sharingif.cube.communication.http.request.HttpRequestInfo;
import com.sharingif.cube.web.vert.x.http.VertXHttpRequest;
import com.sharingif.cube.web.vert.x.http.VertXHttpResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * VertXRequestInfo
 * 2017年7月28日 上午1:04:00
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXRequestInfo extends HttpRequestInfo<VertXHttpRequest, VertXHttpResponse> {

	private RoutingContext routingContext;

	public VertXRequestInfo(
			String mediaType
			,String lookupPath
			,Locale locale
			,String method
			,VertXHttpRequest request
			,VertXHttpResponse response
			,RoutingContext routingContext
		) {
		super(mediaType, lookupPath, locale, method, request, response);

		this.routingContext = routingContext;
	}

	public RoutingContext getRoutingContext() {
		return routingContext;
	}
}
