package com.sharingif.cube.web.vert.x.http;

import java.util.Enumeration;

import com.sharingif.cube.communication.http.HttpSession;
import io.vertx.ext.web.Session;

/**
 * HttpSession
 * 2017年1月5日 上午11:29:12
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXHttpSession implements HttpSession {

	private Session session;

	public VertXHttpSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<?> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub

	}

}
