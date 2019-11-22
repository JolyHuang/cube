package com.sharingif.cube.netty.websocket;

import com.sharingif.cube.netty.websocket.handler.WebSocketServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

public class WebSocketServerBootstrap implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private int port;
    private int workerGroupThreadNumber;
    private WebSocketServerChannelInitializer webSocketServerChannelInitializer;

    public WebSocketServerBootstrap() {
        this.workerGroupThreadNumber = 200;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public void setWorkerGroupThreadNumber(int workerGroupThreadNumber) {
        this.workerGroupThreadNumber = workerGroupThreadNumber;
    }
    public void setWebSocketServerChannelInitializer(WebSocketServerChannelInitializer webSocketServerChannelInitializer) {
        this.webSocketServerChannelInitializer = webSocketServerChannelInitializer;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupThreadNumber);

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(webSocketServerChannelInitializer);

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            logger.info("Started server Websocket");
            channelFuture.channel().closeFuture().sync();
        }finally{
            bossGroup.shutdownGracefully().syncUninterruptibly();
            workerGroup.shutdownGracefully().syncUninterruptibly();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("Starting server Websocket");
                try {
                    start();
                } catch (InterruptedException e) {
                    logger.error("Started server Websocket error");
                }
                logger.info("Closed server Websocket");
            }
        }).start();
    }
}
