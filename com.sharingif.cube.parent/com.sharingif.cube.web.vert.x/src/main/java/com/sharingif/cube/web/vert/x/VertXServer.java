package com.sharingif.cube.web.vert.x;

import com.sharingif.cube.communication.handler.DispatcherHandler;
import com.sharingif.cube.web.vert.x.request.ExtendedRoutingContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.InitializingBean;

/**
 * Vert.x服务 2016年12月7日 下午8:45:29
 * 
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXServer extends AbstractVerticle implements InitializingBean {
	
	private String host;
	private int port;
	private String contextPath;
	private String staticPath;
	private DispatcherHandler<ExtendedRoutingContext> dispatcherHandler;

	private VertxOptions vertxOptions;
	
	public VertXServer() {

		vertxOptions = new VertxOptions();
		vertxOptions.setWorkerPoolSize(200);

	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
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
		
		String basePath = new StringBuilder("/").append(getContextPath()).toString();

		router.post().handler(BodyHandler.create());
		router.put().handler(BodyHandler.create());
		String allPath = new StringBuilder(basePath).append("/*").toString();
		router.route(allPath).blockingHandler(routingContext -> {
			getDispatcherHandler().doDispatch(new ExtendedRoutingContext(getContextPath(), routingContext));
		},false);
		
		server.requestHandler(router::accept).listen(getPort(),getHost());
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		final Vertx vertx = Vertx.vertx(getVertxOptions());
		vertx.deployVerticle(this);
	}

}
