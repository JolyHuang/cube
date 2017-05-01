package com.sharingif.cube.web.vert.x.request;

import io.vertx.ext.web.RoutingContext;

/**
 * 扩展vert.x的RoutingContext添加contextPath属相
 * 2016年12月18日 下午10:29:31
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExtendedRoutingContext {
	
	private String contextPath;
	private RoutingContext routingContext;
	
	public String getContextPath() {
		return contextPath;
	}
	public RoutingContext getRoutingContext() {
		return routingContext;
	}

	public ExtendedRoutingContext(String contextPath, RoutingContext routingContext) {
		this.contextPath = contextPath;
		this.routingContext = routingContext;
	}

}
