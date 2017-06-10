package org.com.sharingif.cube.communication.tcp.netty;

import org.junit.Test;

/**
 * TestNettyTcpServer
 * 2016年4月12日 下午9:38:55
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class TestNettyTcpServer {
	
	NettyNioTcpServer nettyTcpServer = null;
	
	@Test
	public void testStartTestNettyTcpServer() {
		nettyTcpServer = new NettyNioTcpServer();
		nettyTcpServer.setNetGropThreadNumber(1);
		nettyTcpServer.setWorkerGropThreadNumber(50);
		nettyTcpServer.setHost("127.0.0.1");
		nettyTcpServer.setPort(8090);
		nettyTcpServer.setBacklog(0);
		
		nettyTcpServer.start();
	};
	
	@Test
	public void testShutdownTestNettyTcpServer() {
		nettyTcpServer.shutdown();
	}
	
	@Test
	public void testRestartTestNettyTcpServer() {
		nettyTcpServer.restart();
	}
	
	public static void main(String[] args) {
		new TestNettyTcpServer().testStartTestNettyTcpServer();
	}
	
}
