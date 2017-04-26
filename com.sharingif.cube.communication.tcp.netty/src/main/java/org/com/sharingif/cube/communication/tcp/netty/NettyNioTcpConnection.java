package org.com.sharingif.cube.communication.tcp.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.communication.exception.CommunicationException;
import com.sharingif.cube.communication.transport.Connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * Netty nio tcp transport
 * 2016年4月12日 下午10:00:28
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NettyNioTcpConnection implements Connection<byte[], byte[]> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static EventLoopGroup workerEventLoopGroup;
	
	private String host;
	private int port;
	private int workerGropThreadNumber;
	
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
	public int getWorkerGropThreadNumber() {
		return workerGropThreadNumber;
	}
	public void setWorkerGropThreadNumber(int workerGropThreadNumber) {
		this.workerGropThreadNumber = workerGropThreadNumber;
	}
	
	@Override
	public byte[] connect(final byte[] data) throws CommunicationException {
		
		if(workerEventLoopGroup == null) {
			workerEventLoopGroup = new NioEventLoopGroup(workerGropThreadNumber);
		}
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(workerEventLoopGroup)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel channel) throws Exception {
					channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						
						@Override
						public void channelActive(ChannelHandlerContext ctx) throws Exception {
							logger.info("channel active");
						}

						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
							try {
								ByteBuf byteBuf = ((ByteBuf)msg);
								byte[] data = new byte[byteBuf.readableBytes()];
								byteBuf.readBytes(data);
								
						    } finally {
						        ReferenceCountUtil.release(msg);
						    }
						}

						@Override
						public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
							logger.error("Channel handler error", cause);
							ctx.close();
						}

					});
				}
			})
			.option(ChannelOption.SO_KEEPALIVE, true);
		
			ChannelFuture channelFuture = null;
			try {
				channelFuture = bootstrap.connect(host, port).sync();
			} catch (InterruptedException e) {
				throw new CommunicationException("connect to host error", e);
			}
		
		try {
			channelFuture.channel().writeAndFlush(Unpooled.buffer(data.length).writeBytes(data)).sync();
		} catch (InterruptedException e) {
			throw new CommunicationException("transport exception", e);
		}
		return null;
	}
	
}
