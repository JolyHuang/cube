package com.sharingif.cube.web.vert.x;

import org.springframework.beans.factory.InitializingBean;

import com.sharingif.cube.communication.handler.DispatcherHandler;
import com.sharingif.cube.web.vert.x.request.ExtendedRoutingContext;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

/**
 * Vert.x服务 2016年12月7日 下午8:45:29
 * 
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXServer extends AbstractVerticle implements InitializingBean {
	
	private int port;
	private String contextPath;
	private String staticPath;
	private Long sessionTimeOut = 15*60*1000L;
	private DispatcherHandler<ExtendedRoutingContext> dispatcherHandler;

	private VertxOptions vertxOptions;
	
	public VertXServer() {

		vertxOptions = new VertxOptions();
		vertxOptions.setWorkerPoolSize(200);

	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getStaticPath() {
		return staticPath;
	}
	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
	public Long getSessionTimeOut() {
		return sessionTimeOut;
	}
	public void setSessionTimeOut(Long sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}
	public DispatcherHandler<ExtendedRoutingContext> getDispatcherHandler() {
		return dispatcherHandler;
	}
	public void setDispatcherHandler(DispatcherHandler<ExtendedRoutingContext> dispatcherHandler) {
		this.dispatcherHandler = dispatcherHandler;
	}
	public VertxOptions getVertxOptions() {
		return vertxOptions;
	}
	public void setVertxOptions(VertxOptions vertxOptions) {
		this.vertxOptions = vertxOptions;
	}

	@Override
	public void start() throws Exception {
		
		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);

		router.route().handler(CookieHandler.create());

		SessionStore store = LocalSessionStore.create(vertx, "vertx.session", sessionTimeOut);
		SessionHandler sessionHandler = SessionHandler.create(store);
		router.route().handler(sessionHandler);
		
		String basePath = new StringBuilder("/").append(getContextPath()).toString();

		router.post().handler(BodyHandler.create());
		router.put().handler(BodyHandler.create());
		String allPath = new StringBuilder(basePath).append("/*").toString();
		router.route(allPath).blockingHandler(routingContext -> {
			getDispatcherHandler().doDispatch(new ExtendedRoutingContext(getContextPath(), routingContext));
		},false);
		
		server.requestHandler(router::accept).listen(getPort(), "0.0.0.0");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		final Vertx vertx = Vertx.vertx(getVertxOptions());
		vertx.deployVerticle(this);
	}

}
