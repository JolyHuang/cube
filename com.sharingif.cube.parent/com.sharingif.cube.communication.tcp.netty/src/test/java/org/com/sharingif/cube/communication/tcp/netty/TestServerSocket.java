package org.com.sharingif.cube.communication.tcp.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TestServerSocket
 * 2016年4月17日 下午6:36:43
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class TestServerSocket {
	
	private static ServerSocket server;

	public static void main(String[] args) throws IOException {
		server = new ServerSocket(8090);
		Socket socket = server.accept();
		
		System.out.println("服务端获得连接");
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		
		System.out.println("服务端准备接收数据");
		BufferedReader input = new BufferedReader(new InputStreamReader(in));
		String inputLine = input.readLine();
		System.out.println("服务端接收数据:"+inputLine);
		
		System.out.println("服务端准备发送数据:");
		input = new BufferedReader(new InputStreamReader(System.in));
		inputLine = input.readLine();
		out.write(inputLine.getBytes());
		out.flush();
	}

}
