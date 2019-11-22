package com.sharingif.cube.netty.websocket.view;

import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.view.AbstractJsonView;
import com.sharingif.cube.netty.websocket.request.WebSocketRequestContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ChannelView extends AbstractJsonView {

    @Override
    public void view(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {

        String jsonData =  getResponseData(returnValue, exceptionContent == null ? null: exceptionContent.getCubeException());

        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(jsonData);

        WebSocketRequestContext webSocketRequestContext = (WebSocketRequestContext)requestContext;
        ChannelHandlerContext channelHandlerContext = webSocketRequestContext.getChannelHandlerContext();
        channelHandlerContext.writeAndFlush(textWebSocketFrame);
    }

}
