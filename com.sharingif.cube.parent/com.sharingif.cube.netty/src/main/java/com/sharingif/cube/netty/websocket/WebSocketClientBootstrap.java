package com.sharingif.cube.netty.websocket;

import com.sharingif.cube.netty.websocket.handler.WebSocketClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.net.URI;

public class WebSocketClientBootstrap implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private URI uri;
    private WebSocketClientHandler webSocketClientHandler;

    public WebSocketClientBootstrap(String uri) {
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

    public void start() throws InterruptedException {
        String scheme = getScheme();
        String host = getHost();
        int port = getPort(scheme);

        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
            logger.error("Only WS(S) is supported");
            return;
        }

        final WebSocketClientHandler handler = new WebSocketClientHandler(
                WebSocketClientHandshakerFactory.newHandshaker(
                        uri, WebSocketVersion.V13, null, false, EmptyHttpHeaders.INSTANCE, 65536));

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
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
                });

        bootstrap.connect(host, port).sync();
        handler.handshakeFuture().sync();
        logger.info("Started client Websocket");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("Starting client Websocket");
                try {
                    start();
                } catch (InterruptedException e) {
                    logger.error("Started client Websocket error");
                }
                logger.info("Closed client Websocket");
            }
        }).start();
    }

}
