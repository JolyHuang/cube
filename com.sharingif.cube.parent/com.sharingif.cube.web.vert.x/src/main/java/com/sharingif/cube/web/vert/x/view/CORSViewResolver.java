package com.sharingif.cube.web.vert.x.view;

import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestInfo;
import io.vertx.ext.web.handler.CorsHandler;

/**
 * 处理跨域
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/30 上午11:51
 */
public class CORSViewResolver implements ViewResolver {

    private static final CORSView corsView = new CORSView();

    @Override
    public View resolveView(RequestInfo<?> requestInfo, Object returnValue, ExceptionContent exceptionContent) {
        if((returnValue instanceof CorsHandler)) {
            return corsView;
        }

        return null;
    }
}
