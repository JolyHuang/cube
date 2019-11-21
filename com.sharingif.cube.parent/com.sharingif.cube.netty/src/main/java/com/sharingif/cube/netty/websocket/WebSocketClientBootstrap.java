package com.sharingif.cube.netty.websocket;

import com.sharingif.cube.netty.websocket.handler.WebSocketClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.net.URI;

public class WebSocketClientBootstrap implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private URI uri;
    private WebSocketClientChannelInitializer webSocketClientChannelInitializer;

    public WebSocketClientBootstrap(String uri) {
        this.uri = URI.create(uri);
    }

    public void setWebSocketClientChannelInitializer(WebSocketClientChannelInitializer webSocketClientChannelInitializer) {
        this.webSocketClientChannelInitializer = webSocketClientChannelInitializer;
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

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(webSocketClientChannelInitializer);

        bootstrap.connect(host, port).sync();
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
