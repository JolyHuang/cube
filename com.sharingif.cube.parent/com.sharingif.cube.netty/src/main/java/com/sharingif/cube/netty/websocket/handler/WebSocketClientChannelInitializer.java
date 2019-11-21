package com.sharingif.cube.netty.websocket.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.net.URI;

public class WebSocketClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private URI uri;
    private WebSocketClientHandler webSocketClientHandler;

    public WebSocketClientChannelInitializer(String uri) {
        this.uri = URI.create(uri);
    }
    public void setWebSocketClientHandler(WebSocketClientHandler webSocketClientHandler) {
        this.webSocketClientHandler = webSocketClientHandler;
    }

    protected String getScheme() {
        return uri.getScheme() == null? "ws" : uri.getScheme();
    }

    protected String getHost() {
        return uri.getHost() == null? "127.0.0.1" : uri.getHost();
    }

    protected int getPort(String scheme) {
        int port;
        if (uri.getPort() == -1) {
            if ("ws".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("wss".equalsIgnoreCase(scheme)) {
                port = 443;
            } else {
                port = -1;
            }
        } else {
            port = uri.getPort();
        }

        return port;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        String scheme = getScheme();
        String host = getHost();
        int port = getPort(scheme);

        ChannelPipeline pipeline = socketChannel.pipeline();
        if ("wss".equalsIgnoreCase(uri.getScheme())) {
            SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            pipeline.addLast(sslContext.newHandler(socketChannel.alloc(), host, port));
        }
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(webSocketClientHandler);
    }

}
