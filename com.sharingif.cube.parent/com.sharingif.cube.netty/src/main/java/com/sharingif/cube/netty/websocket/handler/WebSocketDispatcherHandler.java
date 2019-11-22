package com.sharingif.cube.netty.websocket.handler;

import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.handler.AbstractDispatcherHandler;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.netty.websocket.request.WebSocketRequest;
import com.sharingif.cube.netty.websocket.request.WebSocketRequestContext;
import io.netty.handler.codec.http.HttpMethod;

import java.util.Locale;

public class WebSocketDispatcherHandler extends AbstractDispatcherHandler<WebSocketRequest> {

    @Override
    protected HandlerMethodContent getHandlerMethodContent(WebSocketRequest request) {
        WebSocketRequestContext webSocketRequestContext = new WebSocketRequestContext(
                MediaType.APPLICATION_JSON.getType()
                , request.getJsonRequest().getLookupPath()
                , Locale.CHINESE
                , HttpMethod.POST.name()
                , request.getJsonRequest()
                , request.getChannelHandlerContext()
        );

        HandlerMethodContent handlerMethodContent = new HandlerMethodContent(null
                ,null
                ,null
                ,null
                ,webSocketRequestContext);

        return handlerMethodContent;
    }
}
