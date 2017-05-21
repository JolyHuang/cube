package com.sharingif.cube.web.vert.x.handler.chain;

import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.request.RequestInfo;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

/**
 * vert.x 处理器异常处理责任链
 * 2017/5/20 下午9:32
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXDispatcherHandlerExceptionChain extends AbstractHandlerMethodChain<HttpHandlerMethodContent> {

    @Override
    public void before(HttpHandlerMethodContent handlerMethodContent) throws CubeException {

    }

    @Override
    public void after(HttpHandlerMethodContent handlerMethodContent) throws CubeException {

    }

    @SuppressWarnings("unchecked")
	@Override
    public void exception(HttpHandlerMethodContent content, Exception exception) throws CubeException {
        RequestInfo<RoutingContext> requestInfo = (RequestInfo<RoutingContext>) content.getRequestInfo();

        requestInfo.getRequest().response()
                .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .setStatusCode(404)
                .end();
    }
}
