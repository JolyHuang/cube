package com.sharingif.cube.netty.websocket.request;

import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.core.request.RequestContextResolver;

import java.util.Locale;

public class WebSocketRequestContextResolver implements RequestContextResolver<WebSocketRequest, WebSocketRequestContext> {

    @Override
    public WebSocketRequestContext resolveRequest(WebSocketRequest request) {
        WebSocketRequestContext webSocketRequestContext = new WebSocketRequestContext(
                MediaType.APPLICATION_JSON.getType()
                , request.getJsonRequest().getLookupPath()
                , Locale.CHINESE
                , null
                , request.getJsonRequest()
                , request.getChannelHandlerContext()
        );

        return webSocketRequestContext;
    }

}
