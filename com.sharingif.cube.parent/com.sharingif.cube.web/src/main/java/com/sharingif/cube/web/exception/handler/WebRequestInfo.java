package com.sharingif.cube.web.exception.handler;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;

/**
 * web请求消息
 * 2016年5月8日 下午8:49:29
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class WebRequestInfo {
	
	public WebRequestInfo(
			HttpRequest request
			,HttpResponse response
			) {
		this.request = request;
		this.response = response;
	}
	
	private HttpRequest request;
	private HttpResponse response;

	public HttpRequest getRequest() {
		return request;
	}
	public void setRequest(HttpRequest request) {
		this.request = request;
	}
	public HttpResponse getResponse() {
		return response;
	}
	public void setResponse(HttpResponse response) {
		this.response = response;
	}

}
