package com.sharingif.cube.netty.websocket.request;

import com.sharingif.cube.netty.websocket.request.JsonRequest;
import io.netty.channel.ChannelHandlerContext;

public class WebSocketRequest {

    private ChannelHandlerContext channelHandlerContext;
    private JsonRequest<String> jsonRequest;

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public JsonRequest<String> getJsonRequest() {
        return jsonRequest;
    }

    public void setJsonRequest(JsonRequest<String> jsonRequest) {
        this.jsonRequest = jsonRequest;
    }

}
