package com.sharingif.cube.web.vert.x.view;

import com.sharingif.cube.core.view.View;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.web.vert.x.request.VertXRequestContext;

import io.vertx.ext.web.handler.StaticHandler;

/**
 * 处理静态页面
 * 2017/5/26 下午8:01
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXStaticView implements View {

    @Override
    public void view(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {
        VertXRequestContext vertXRequestContext = (VertXRequestContext)requestContext;

        StaticHandler staticHandler = (StaticHandler)returnValue;
        staticHandler.handle(vertXRequestContext.getRoutingContext());
    }
}
