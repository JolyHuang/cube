package com.sharingif.cube.netty.websocket.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * 判断是否是http握手请求
     * @param fullHttpRequest
     * @return
     */
    protected boolean isUpgradeWebsocket(FullHttpRequest fullHttpRequest) {
        return "websocket".equals(fullHttpRequest.headers().get("Upgrade"));
    }

    protected void badRequest(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        channelHandlerContext.write(fullHttpResponse);
        ChannelFuture future = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if(!isUpgradeWebsocket(fullHttpRequest)) {
            badRequest(channelHandlerContext, fullHttpRequest);
            return;
        }

        channelHandlerContext.fireChannelRead(fullHttpRequest.retain());
    }

}
