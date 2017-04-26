package org.com.sharingif.cube.communication.tcp.netty;

/**
 * TODO
 * 2016年4月12日 下午9:59:02
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class TestNettyNioTcpClient {
	
	public static void main(String[] args) {
		NettyNioTcpConnection nettyNioTcpTransport = new NettyNioTcpConnection();
		nettyNioTcpTransport.setHost("127.0.0.1");
		nettyNioTcpTransport.setPort(8090);
		nettyNioTcpTransport.setWorkerGropThreadNumber(50);
		
		nettyNioTcpTransport.connect("sdfsferfsddf".getBytes());
		
	}
	
}
