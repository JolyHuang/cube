package com.sharingif.cube.web.vert.x;

import com.sharingif.cube.communication.handler.DispatcherHandler;
import com.sharingif.cube.web.vert.x.request.ExtendedRoutingContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.TemplateEngine;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import org.springframework.beans.factory.InitializingBean;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

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
	private TemplateEngine templateEngine;
	
	private VertxOptions vertxOptions;
	
	public VertXServer() {
		staticPath = "static";
		templateEngine = ThymeleafTemplateEngine.create();
		((ThymeleafTemplateEngine)templateEngine).getThymeleafTemplateEngine().setTemplateResolver(new ClassLoaderTemplateResolver());
		
		vertxOptions = new VertxOptions();
		vertxOptions.setWorkerPoolSize(200);

		host = "localhost";
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
	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}
	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
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
		router.route(allPath)
				.handler(ctx -> {
					ctx.request().setExpectMultipart(true);
					ctx.next();
				}).blockingHandler(routingContext -> {
			getDispatcherHandler().doDispatch(new ExtendedRoutingContext(getContextPath(), routingContext));
		},false);
		
		server.requestHandler(router::accept).listen(getPort(),getHost());
	}
	
	protected void staticRouter(Router router, String basePath) {
		String path = new StringBuilder(basePath).append("/").append(getStaticPath()).append("/*").toString();
		
		StaticHandler staticHandler = StaticHandler.create();
		staticHandler.setWebRoot(getStaticPath());
		
		router.route(path).blockingHandler(staticHandler,false);
	}
	
	protected void webViewRouter(Router router, String basePath) {
		String webViewPath = new StringBuilder(basePath).append(".*\\.html").toString();
		
		router.routeWithRegex(webViewPath).blockingHandler(routingContext -> {
			HttpServerRequest httpServerRequest = routingContext.request();
			String templatePath = httpServerRequest.path().replace(new StringBuilder("/").append(getContextPath()).toString(), "");
			
			templateEngine.render(routingContext, templatePath, res -> {
				if (res.succeeded()) {
					routingContext.response().end(res.result());
				} else {
					routingContext.fail(res.cause());
		        }
			});
		},false);
		
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		final Vertx vertx = Vertx.vertx(getVertxOptions());
		vertx.deployVerticle(this);
	}

}
