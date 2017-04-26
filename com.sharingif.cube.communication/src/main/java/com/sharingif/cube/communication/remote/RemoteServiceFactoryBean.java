package com.sharingif.cube.communication.remote;

import org.springframework.beans.factory.FactoryBean;

/**
 * RemoteServiceFactoryBean
 * 2017年1月16日 下午4:58:37
 * @author Joly
 * @version v1.0
 * @param <T>
 * @since v1.0
 */
public class RemoteServiceFactoryBean implements FactoryBean<Object> {
	
	public static final String PROXY_OBJECT = "proxyObject";
	
	private Object proxyObject;
	
	public Object getProxyObject() {
		return proxyObject;
	}
	public void setProxyObject(Object proxyObject) {
		this.proxyObject = proxyObject;
	}

	@Override
	public Object getObject() throws Exception {
		return proxyObject;
	}

	@Override
	public Class<?> getObjectType() {
		return proxyObject.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
