package com.sharingif.cube.netty.websocket.handler;

import com.sharingif.cube.communication.handler.AbstractDispatcherHandler;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.netty.websocket.request.WebSocketRequest;

public class WebSocketDispatcherHandler extends AbstractDispatcherHandler<WebSocketRequest> {

    @Override
    protected HandlerMethodContent getHandlerMethodContent(WebSocketRequest request) {
        return null;
    }
}
