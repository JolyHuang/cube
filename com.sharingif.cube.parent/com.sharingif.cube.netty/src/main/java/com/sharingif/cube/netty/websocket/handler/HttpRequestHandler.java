package com.sharingif.cube.netty.websocket.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        // 如果HTTP解码失败，返回HHTP异常
        if (!fullHttpRequest.decoderResult().isSuccess() || (!"websocket".equals(fullHttpRequest.headers().get("Upgrade")))) {
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
            channelHandlerContext.write(fullHttpResponse);
            ChannelFuture future = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            future.addListener(ChannelFutureListener.CLOSE);
            return;
        }

        channelHandlerContext.fireChannelRead(fullHttpRequest.retain());
    }

}
