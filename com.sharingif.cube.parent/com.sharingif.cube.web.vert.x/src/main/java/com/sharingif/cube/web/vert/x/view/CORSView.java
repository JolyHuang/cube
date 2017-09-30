package com.sharingif.cube.web.vert.x.view;

import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.vert.x.request.VertXRequestInfo;
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
    public void view(RequestInfo<?> requestInfo, Object returnValue, ExceptionContent exceptionContent) {
        VertXRequestInfo vertXRequestInfo = (VertXRequestInfo)requestInfo;
        CorsHandler corsHandler = (CorsHandler)returnValue;
        corsHandler.handle(vertXRequestInfo.getRoutingContext());
    }

}
