package com.sharingif.cube.security.web.handler.chain.session;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.security.web.access.ISessionExpireHandler;

/**
 * 处理session是否失效
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/3/2 上午10:54
 */
public class SessionExpireChain extends AbstractHandlerMethodChain {

    private ISessionExpireHandler sessionExpireHandler;

    public ISessionExpireHandler getSessionExpireHandler() {
        return sessionExpireHandler;
    }

    public void setSessionExpireHandler(ISessionExpireHandler sessionExpireHandler) {
        this.sessionExpireHandler = sessionExpireHandler;
    }

    @Override
    public void before(HandlerMethodContent handlerMethodContent) throws CubeException {
        HttpRequestContext<HttpRequest,HttpResponse> httpRequestContext = handlerMethodContent.getRequestContext();

        sessionExpireHandler.handleUserExpire(httpRequestContext.getRequest());
    }

    @Override
    public void after(HandlerMethodContent handlerMethodContent) throws CubeException {

    }

}
