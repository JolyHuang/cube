package com.sharingif.cube.web.springmvc.http;

import java.util.Enumeration;

import com.sharingif.cube.communication.http.HttpSession;

/**
 * SpringMVCHttpSession
 * 2016年12月29日 下午9:44:31
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SpringMVCHttpSession implements HttpSession {
	
	private javax.servlet.http.HttpSession httpSession;
	
	public SpringMVCHttpSession(javax.servlet.http.HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public javax.servlet.http.HttpSession getHttpSession() {
		return httpSession;
	}

	@Override
	public String getId() {
		return getHttpSession().getId();
	}

	@Override
	public Object getAttribute(String name) {
		return getHttpSession().getAttribute(name);
	}

	@Override
	public Enumeration<?> getAttributeNames() {
		return getHttpSession().getAttributeNames();
	}

	@Override
	public void setAttribute(String name, Object value) {
		getHttpSession().setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		getHttpSession().removeAttribute(name);
	}

	@Override
	public void invalidate() {
		getHttpSession().invalidate();
	}
	
	

}
