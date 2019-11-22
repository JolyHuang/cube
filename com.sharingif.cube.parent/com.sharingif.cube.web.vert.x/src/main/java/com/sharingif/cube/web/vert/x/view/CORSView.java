package com.sharingif.cube.web.vert.x.view;

import com.sharingif.cube.core.view.View;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.web.vert.x.request.VertXRequestContext;

import io.vertx.ext.web.handler.CorsHandler;

/**
 * 处理跨域
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/30 上午11:53
 */
public class CORSView implements View {

    @Override
    public void view(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {
        VertXRequestContext vertXRequestContext = (VertXRequestContext)requestContext;
        CorsHandler corsHandler = (CorsHandler)returnValue;
        corsHandler.handle(vertXRequestContext.getRoutingContext());
    }

}
