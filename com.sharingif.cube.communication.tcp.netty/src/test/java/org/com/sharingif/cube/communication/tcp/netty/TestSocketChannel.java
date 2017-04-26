package org.com.sharingif.cube.communication.tcp.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * TODO
 * 2016年4月17日 下午8:29:00
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class TestSocketChannel {
	
	public static void main(String[] args) throws IOException {
		SocketAddress remote = new InetSocketAddress("127.0.0.1", 8090);
		SocketChannel socketChannel = SocketChannel.open(remote);
		
		System.out.println("客户端等待输入:");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String inputLine = input.readLine();
		byte[] inputLineByte = inputLine.getBytes();
		
		ByteBuffer byteBuffer2 = ByteBuffer.allocate(inputLineByte.length);
		byteBuffer2.put(inputLineByte);
		byteBuffer2.flip();
		socketChannel.write(byteBuffer2);
		byteBuffer2.clear();
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(74);
		WritableByteChannel out = Channels.newChannel(System.out);
		
		while(socketChannel.read(byteBuffer)!= -1) {
			byteBuffer.flip();
			out.write(byteBuffer);
			byteBuffer.clear();
			System.out.println("========");
			
			System.out.println("客户端等待输入:");
			input = new BufferedReader(new InputStreamReader(System.in));
			inputLine = input.readLine();
			inputLineByte = inputLine.getBytes();
			
			byteBuffer2 = ByteBuffer.allocate(inputLineByte.length);
			byteBuffer2.put(inputLineByte);
			byteBuffer2.flip();
			socketChannel.write(byteBuffer2);
			byteBuffer2.clear();
		}
		
		
		
	}

}
