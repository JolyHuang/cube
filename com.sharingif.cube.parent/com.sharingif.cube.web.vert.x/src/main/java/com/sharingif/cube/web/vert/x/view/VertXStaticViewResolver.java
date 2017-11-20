package com.sharingif.cube.web.vert.x.view;

import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestInfo;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * 处理静态页面
 * 2017/5/26 下午7:59
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXStaticViewResolver implements ViewResolver {

    private final VertXStaticView vertXStaticView = new VertXStaticView();


    @Override
    public View resolveView(RequestInfo<?> requestInfo, Object returnValue, ExceptionContent exceptionContent) {
        if((returnValue instanceof StaticHandler)) {
            return vertXStaticView;
        }

        return null;
    }
}
