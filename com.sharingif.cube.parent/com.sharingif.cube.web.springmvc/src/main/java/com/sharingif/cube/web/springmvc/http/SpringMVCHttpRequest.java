package com.sharingif.cube.web.springmvc.http;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * SpringMVCHttpRequest
 * 2016年12月29日 下午9:38:22
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SpringMVCHttpRequest implements HttpRequest {
	
	private HttpServletRequest httpServletRequest;
	private SpringMVCHttpSession httpSession;
	
	public SpringMVCHttpRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	@Override
	public int getContentLength() {
		return getHttpServletRequest().getContentLength();
	}

	@Override
	public String getContentType() {
		return getHttpServletRequest().getContentType();
	}

	@Override
	public String getParameter(String name) {
		return getHttpServletRequest().getParameter(name);
	}

	@Override
	public void setAttribute(String name, Object o) {
		getHttpServletRequest().setAttribute(name, o);
	}

	@Override
	public String getHeader(String name) {
		return getHttpServletRequest().getHeader(name);
	}

	@Override
	public Enumeration<?> getHeaders(String name) {
		return getHttpServletRequest().getHeaders(name);
	}

	@Override
	public Enumeration<?> getHeaderNames() {
		return getHttpServletRequest().getHeaderNames();
	}

	@Override
	public String getMethod() {
		return getHttpServletRequest().getMethod();
	}

	@Override
	public String getContextPath() {
		return getHttpServletRequest().getContextPath();
	}

	@Override
	public String getRequestURI() {
		return getHttpServletRequest().getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return getHttpServletRequest().getRequestURL();
	}

	@Override
	public String getQueryString() {
		return getHttpServletRequest().getQueryString();
	}

	@Override
	public HttpSession getSession(boolean create) {
		if(httpSession == null) {
			javax.servlet.http.HttpSession httpServletSession = getHttpServletRequest().getSession(create);
			if(httpServletSession == null) {
				return null;
			}
			httpSession = new SpringMVCHttpSession(httpServletSession);
		}
		return httpSession;
	}

	@Override
	public HttpSession getSession() {
		return getSession(false);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return getHttpServletRequest().getParameterMap();
	}

}
