package com.sharingif.cube.web.springmvc.http;

import javax.servlet.http.HttpServletResponse;

import com.sharingif.cube.communication.http.HttpResponse;

/**
 * SpringMVCHttpResponse
 * 2016年12月29日 下午9:52:19
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SpringMVCHttpResponse implements HttpResponse {
	
	private HttpServletResponse response;
	
	public SpringMVCHttpResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public void addHeader(String name, String value) {
		getResponse().addHeader(name, value);
	}

	@Override
	public void setStatus(int sc) {
		getResponse().setStatus(sc);
	}

}
