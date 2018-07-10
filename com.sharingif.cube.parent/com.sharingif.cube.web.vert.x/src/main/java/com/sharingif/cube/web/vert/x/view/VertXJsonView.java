package com.sharingif.cube.web.vert.x.view;


import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.view.AbstractJsonView;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.web.vert.x.request.VertXRequestContext;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;

/**
 * VertXJsonView
 * 2016年12月28日 下午3:52:05
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXJsonView extends AbstractJsonView {
	
	@Override
	public void view(RequestContext<?> requestContext, Object returnValue,ExceptionContent exceptionContent) {

		VertXRequestContext vertXRequestContext = (VertXRequestContext)requestContext;


		Buffer buffer = Buffer.buffer(getResponseData(returnValue, exceptionContent == null ? null: exceptionContent.getCubeException()));

		HttpServerResponse httpServerResponse = vertXRequestContext.getResponse().getHttpServerResponse();
		String accessControlAllowOrigin = vertXRequestContext.getRequest().getHttpServerRequest().headers().get(HttpHeaders.ORIGIN);
		if(!StringUtils.isTrimEmpty(accessControlAllowOrigin)) {
			httpServerResponse.putHeader(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN,accessControlAllowOrigin);
		}


		httpServerResponse
				.putHeader(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true")
				.putHeader(HttpHeaderNames.CACHE_CONTROL, "no-store")
				.putHeader(HttpHeaderNames.PRAGMA, "no-cache")
				.putHeader(HttpHeaderNames.EXPIRES, "0")
				.putHeader(HttpHeaderNames.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
				.putHeader(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(buffer.length()))
				.write(buffer)
				.end();
		
	}

}
