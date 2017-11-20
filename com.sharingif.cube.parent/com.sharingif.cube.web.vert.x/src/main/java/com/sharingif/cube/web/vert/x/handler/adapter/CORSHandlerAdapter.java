package com.sharingif.cube.web.vert.x.handler.adapter;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.adapter.HandlerAdapter;
import com.sharingif.cube.core.request.RequestContext;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;

/**
 * 处理跨域
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/30 上午11:46
 */
public class CORSHandlerAdapter implements HandlerAdapter<RoutingContext,CorsHandler> {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof CorsHandler;
    }

    @Override
    public Object handle(RequestContext<RoutingContext> request, CorsHandler handler) throws CubeException {
        return handler;
    }
}
