package com.sharingif.cube.netty.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.netty.websocket.*;
import com.sharingif.cube.netty.websocket.request.JsonRequest;
import com.sharingif.cube.netty.websocket.request.WebSocketRequest;
import com.sharingif.cube.netty.websocket.response.JsonResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimeZone;

@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper objectMapper;
    private WebSocketDispatcherHandler webSocketDispatcherHandler;

    public TextWebSocketFrameHandler() {
        objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
    }

    public void setWebSocketDispatcherHandler(WebSocketDispatcherHandler webSocketDispatcherHandler) {
        this.webSocketDispatcherHandler = webSocketDispatcherHandler;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            // 握手成功，从该ChannelHandler中移除HttpRequestHandler，因此将不会接受任何HTTP消息了
            ctx.pipeline().remove(HttpRequestHandler.class);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    protected String objectToJsonString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        String jsonData =  msg.text();

        JsonRequest<String> jsonRequest;
        try {
            TypeFactory typeFactory = this.objectMapper.getTypeFactory();

            JavaType jsonDataJavaType = typeFactory.constructParametricType(JsonRequest.class, String.class);

            jsonRequest = objectMapper.readValue(jsonData, jsonDataJavaType);
        } catch (Exception e) {
            logger.error("error parsing websocket request,request data:{}", jsonData);

            JsonResponse<String> jsonResponse = new JsonResponse<>();
            jsonResponse.setLookupPath("badData");
            jsonResponse.set_tranStatus(Boolean.FALSE);
            jsonResponse.set_exceptionMessage("bad data");
            jsonResponse.set_exceptionLocalizedMessage("bad data");

            String badData = objectToJsonString(jsonResponse);

            ctx.channel().writeAndFlush(new TextWebSocketFrame(badData));

            return;
        }

        WebSocketRequest webSocketRequest = new WebSocketRequest();
        webSocketRequest.setChannelHandlerContext(ctx);
        webSocketRequest.setJsonRequest(jsonRequest);

        webSocketDispatcherHandler.doDispatch(webSocketRequest);
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
