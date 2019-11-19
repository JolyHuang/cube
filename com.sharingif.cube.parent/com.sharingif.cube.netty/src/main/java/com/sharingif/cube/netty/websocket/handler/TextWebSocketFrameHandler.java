package com.sharingif.cube.netty.websocket.handler;

import com.sharingif.cube.netty.websocket.WebSocketChannelGroup;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            // 握手成功，从该ChannelHandler中移除HttpRequestHandler，因此将不会接受任何HTTP消息了
            ctx.pipeline().remove(HttpRequestHandler.class);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        logger.info("removed channel,channelId:{}", channel.id().asLongText());

        WebSocketChannelGroup.removeChannel(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();

        logger.error("channel connection error,channelId:{}", channel.id().asLongText(), cause);
        channel.close();
    }

}
