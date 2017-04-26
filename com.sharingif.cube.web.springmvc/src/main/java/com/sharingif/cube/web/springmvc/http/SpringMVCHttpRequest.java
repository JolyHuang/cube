package com.sharingif.cube.web.springmvc.http;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpSession;

/**
 * SpringMVCHttpRequest
 * 2016年12月29日 下午9:38:22
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SpringMVCHttpRequest implements HttpRequest {
	
	private HttpServletRequest request;
	private SpringMVCHttpSession httpSession;
	
	public SpringMVCHttpRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}



	@Override
	public int getContentLength() {
		return getRequest().getContentLength();
	}

	@Override
	public String getContentType() {
		return getRequest().getContentType();
	}

	@Override
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	@Override
	public void setAttribute(String name, Object o) {
		getRequest().setAttribute(name, o);
	}

	@Override
	public String getHeader(String name) {
		return getRequest().getHeader(name);
	}

	@Override
	public Enumeration<?> getHeaders(String name) {
		return getRequest().getHeaders(name);
	}

	@Override
	public Enumeration<?> getHeaderNames() {
		return getRequest().getHeaderNames();
	}

	@Override
	public String getMethod() {
		return getRequest().getMethod();
	}

	@Override
	public String getContextPath() {
		return getRequest().getContextPath();
	}

	@Override
	public String getRequestURI() {
		return getRequest().getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return getRequest().getRequestURL();
	}

	@Override
	public String getQueryString() {
		return getRequest().getQueryString();
	}

	@Override
	public HttpSession getSession(boolean create) {
		if(httpSession == null) {
			httpSession = new SpringMVCHttpSession(getRequest().getSession(create));
		}
		return httpSession;
	}

	@Override
	public HttpSession getSession() {
		if(httpSession == null) {
			httpSession = new SpringMVCHttpSession(getRequest().getSession());
		}
		return httpSession;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return getRequest().getParameterMap();
	}

}
