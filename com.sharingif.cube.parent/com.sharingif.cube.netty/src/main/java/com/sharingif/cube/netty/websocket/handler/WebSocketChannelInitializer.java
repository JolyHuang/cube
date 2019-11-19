package com.sharingif.cube.netty.websocket.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.net.ssl.SSLEngine;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    private String path;
    private boolean useSSL;
    private SslContext sslContext;
    private HttpRequestHandler httpRequestHandler;
    private TextWebSocketFrameHandler textWebSocketFrameHandler;

    public void setPath(String path) {
        this.path = path;
    }
    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }
    public void setSslContext(SslContext sslContext) {
        this.sslContext = sslContext;
    }
    public void setHttpRequestHandler(HttpRequestHandler httpRequestHandler) {
        this.httpRequestHandler = httpRequestHandler;
    }
    public void setTextWebSocketFrameHandler(TextWebSocketFrameHandler textWebSocketFrameHandler) {
        this.textWebSocketFrameHandler = textWebSocketFrameHandler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        if(useSSL) {
            SSLEngine sslEngine = sslContext.newEngine(socketChannel.alloc());
            pipeline.addLast(new SslHandler(sslEngine, Boolean.TRUE));
        }
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(httpRequestHandler);
        pipeline.addLast(new WebSocketServerProtocolHandler(path));
        pipeline.addLast(textWebSocketFrameHandler);
    }

}
