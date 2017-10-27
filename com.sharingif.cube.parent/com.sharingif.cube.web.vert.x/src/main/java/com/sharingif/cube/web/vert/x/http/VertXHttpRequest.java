package com.sharingif.cube.web.vert.x.http;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpSession;
import io.vertx.core.http.HttpServerRequest;

import java.util.Enumeration;
import java.util.Map;

/**
 * VertXHttpRequest
 * 2016年12月30日 上午11:27:08
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXHttpRequest implements HttpRequest {
	
	private HttpServerRequest httpServerRequest;
	private VertXHttpSession httpSession;
	
	public VertXHttpRequest(HttpServerRequest  httpServerRequest) {
		this.httpServerRequest = httpServerRequest;
	}
	
	public HttpServerRequest getHttpServerRequest() {
		return httpServerRequest;
	}

	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String name, Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getHeader(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<?> getHeaders(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<?> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession(boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setHttpSession(VertXHttpSession httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public HttpSession getSession() {
		return httpSession;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
