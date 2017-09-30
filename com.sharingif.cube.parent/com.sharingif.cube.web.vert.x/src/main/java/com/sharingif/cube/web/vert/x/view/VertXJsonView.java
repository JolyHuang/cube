package com.sharingif.cube.web.vert.x.view;


import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.view.AbstractJsonView;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.vert.x.request.VertXRequestInfo;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

/**
 * VertXJsonView
 * 2016年12月28日 下午3:52:05
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXJsonView extends AbstractJsonView {
	
	@Override
	public void view(RequestInfo<?> requestInfo, Object returnValue,ExceptionContent exceptionContent) {

		VertXRequestInfo vertXRequestInfo = (VertXRequestInfo)requestInfo;
		
		Buffer buffer = Buffer.buffer(getResponseData(returnValue, exceptionContent == null ? null: exceptionContent.getCubeException()));

		vertXRequestInfo.getResponse().getHttpServerResponse()
				.putHeader("Access-Control-Allow-Origin", "*")
				.putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
				.putHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(buffer.length()))
				.write(buffer)
				.end();
		
	}

}
