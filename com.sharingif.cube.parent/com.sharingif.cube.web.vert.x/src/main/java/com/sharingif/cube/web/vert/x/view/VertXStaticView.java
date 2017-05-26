package com.sharingif.cube.web.vert.x.view;

import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestInfo;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * 处理静态页面
 * 2017/5/26 下午8:01
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXStaticView implements View<RoutingContext> {

    @Override
    public void view(RequestInfo<RoutingContext> requestInfo, Object returnValue, ExceptionContent exceptionContent) {
        StaticHandler staticHandler = (StaticHandler)returnValue;
        staticHandler.handle(requestInfo.getRequest());
    }
}
