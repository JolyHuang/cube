package com.sharingif.cube.web.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web请求消息
 * 2016年5月8日 下午8:49:29
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class WebRequestInfo {
	
	public WebRequestInfo(
			HttpServletRequest request
			,HttpServletResponse response
			) {
		this.request = request;
		this.response = response;
	}
	
	private HttpServletRequest request;
	private HttpServletResponse response;

	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
