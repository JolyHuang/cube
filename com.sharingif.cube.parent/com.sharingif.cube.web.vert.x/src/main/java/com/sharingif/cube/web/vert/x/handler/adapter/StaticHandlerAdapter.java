package com.sharingif.cube.web.vert.x.handler.adapter;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.adapter.HandlerAdapter;
import com.sharingif.cube.core.request.RequestContext;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * 处理静态页面
 * 2017/5/26 下午7:35
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class StaticHandlerAdapter implements HandlerAdapter<RoutingContext,StaticHandler> {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof StaticHandler;
    }

    @Override
    public Object handle(RequestContext<RoutingContext> request, StaticHandler handler) throws CubeException {
        return handler ;
    }
}
