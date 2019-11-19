package com.sharingif.cube.netty.websocket.handler;

import com.sharingif.cube.netty.websocket.handler.TextWebSocketFrameHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.net.ssl.SSLEngine;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    private String path;
    private boolean useSSL;
    private SslContext sslContext;

    @Value("${websocket.path}")
    public void setPath(String path) {
        this.path = path;
    }
    @Value("${websocket.use.ssl}")
    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }
    @Resource
    public void setSslContext(SslContext sslContext) {
        this.sslContext = sslContext;
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
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(path));
        pipeline.addLast(new TextWebSocketFrameHandler());
    }

}
