package com.sharingif.cube.netty.websocket.request;

import com.sharingif.cube.core.request.RequestContext;
import io.netty.channel.ChannelHandlerContext;

import java.util.Locale;

public class WebSocketRequestContext extends RequestContext<JsonRequest<String>> {

    private ChannelHandlerContext channelHandlerContext;

    public WebSocketRequestContext(String mediaType, String lookupPath, Locale locale, String method, JsonRequest<String> request, ChannelHandlerContext channelHandlerContext) {
        super(mediaType, lookupPath, locale, method, request);
        this.channelHandlerContext = channelHandlerContext;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

}
