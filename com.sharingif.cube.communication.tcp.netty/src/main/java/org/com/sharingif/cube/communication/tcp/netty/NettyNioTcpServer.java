package org.com.sharingif.cube.communication.tcp.netty;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.communication.exception.CommunicationException;
import com.sharingif.cube.core.util.StringUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * netty Tcp服务
 * 2016年4月12日 下午8:47:13
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NettyNioTcpServer {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static boolean isAlive;
	private static EventLoopGroup netEventLoopGroup;
	private static EventLoopGroup workerEventLoopGroup;
	
	private String host;
	private int port;
	private int netGropThreadNumber;
	private int workerGropThreadNumber;
	private int backlog;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getNetGropThreadNumber() {
		return netGropThreadNumber;
	}
	public void setNetGropThreadNumber(int netGropThreadNumber) {
		this.netGropThreadNumber = netGropThreadNumber;
	}
	public int getWorkerGropThreadNumber() {
		return workerGropThreadNumber;
	}
	public void setWorkerGropThreadNumber(int workerGropThreadNumber) {
		this.workerGropThreadNumber = workerGropThreadNumber;
	}
	public int getBacklog() {
		return backlog;
	}
	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}
	
	public synchronized void start() {
		
		if(isAlive) {
			return;
		}
		isAlive = true;
		
		netEventLoopGroup = new NioEventLoopGroup(getNetGropThreadNumber());
		workerEventLoopGroup = new NioEventLoopGroup(getWorkerGropThreadNumber());
		
		ServerBootstrap serverBootstrap = new ServerBootstrap()
				.channel(NioServerSocketChannel.class)
				.group(netEventLoopGroup, workerEventLoopGroup)
				.option(ChannelOption.SO_BACKLOG, backlog)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

							@Override
							public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
								try {
									ByteBuf byteBuf = ((ByteBuf)msg);
									byte[] data = new byte[byteBuf.readableBytes()];
									byteBuf.readBytes(data);
									
									
									System.out.println(new String(data));
									System.out.println("服务端等待输入:");
									BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
									String inputLine = input.readLine();
									byte[] outInputLine = inputLine.getBytes();
									System.out.println("服务端准备发送:");
									ctx.writeAndFlush(Unpooled.buffer(outInputLine.length).writeBytes(outInputLine)).sync();
							    } finally {
							        ReferenceCountUtil.release(msg);
							    }
							}

							@Override
							public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
								logger.error("Channel handler error", cause);
							}
							
						});
					}
					
				});
		
		try {
			if(!StringUtils.isEmpty(host)) {
				serverBootstrap.bind(host, port).sync();
			} else {
				serverBootstrap.bind(port).sync();
			}
		} catch (InterruptedException e) {
			isAlive = false;
			throw new CommunicationException("interrupted exception", e);
		}
		
	}
	
	public synchronized void shutdown() {
		netEventLoopGroup.shutdownGracefully();
		workerEventLoopGroup.shutdownGracefully();
		
		isAlive = false;
	}
	
	public synchronized void restart() {
		
		if(isAlive) {
			shutdown();
		}
		start();
	}

}
