package com.sharingif.cube.web.vert.x.handler.chain;

import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.web.vert.x.request.VertXRequestInfo;

import io.vertx.core.http.HttpHeaders;

/**
 * vert.x 处理器异常处理责任链
 * 2017/5/20 下午9:32
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXDispatcherHandlerExceptionChain extends AbstractHandlerMethodChain {

    @Override
    public void before(HandlerMethodContent handlerMethodContent) throws CubeException {

    }

    @Override
    public void after(HandlerMethodContent handlerMethodContent) throws CubeException {

    }

	@Override
    public void exception(HandlerMethodContent content, Exception exception) throws CubeException {
    	VertXRequestInfo vertXRequestInfo =  content.getRequestInfo();

    	vertXRequestInfo.getResponse().getHttpServerResponse()
                .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .setStatusCode(404)
                .end();
    }
}
