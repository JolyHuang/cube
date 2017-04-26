package com.sharingif.cube.com.sharingif.cube.web.vert.x.http;

import com.sharingif.cube.communication.http.HttpResponse;

import io.vertx.core.http.HttpServerResponse;

/**
 * VertXHttpResponse
 * 2017年1月5日 上午11:26:43
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXHttpResponse implements HttpResponse {
	
	private HttpServerResponse  httpServerResponse;
	
	public VertXHttpResponse(HttpServerResponse  httpServerResponse) {
		this.httpServerResponse = httpServerResponse;
	}
	
	public HttpServerResponse getHttpServerResponse() {
		return httpServerResponse;
	}

	@Override
	public void addHeader(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStatus(int sc) {
		// TODO Auto-generated method stub

	}

}
