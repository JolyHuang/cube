package org.com.sharingif.cube.communication.tcp.netty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TODO
 * 2016年4月17日 下午4:15:36
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class TestSocket {
	
	private static Socket socket;

	public static void main(String[] args) throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", 8090);
		
		InputStream in = socket.getInputStream();
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); 
		
		System.out.println("客户端等待输入:");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String inputLine = input.readLine();
		System.out.println("客户端准备发送:");
		
		
		pw.println(inputLine);
		pw.flush();
		
		System.out.println("客户端准备接收:");
		byte[] data = new byte[1024];
		while(in.read(data)!= -1){
			System.out.println(new String(data));
			
			inputLine = input.readLine();
			System.out.println("客户端准备发送:");
			
			pw.println(inputLine);
			pw.flush();
			
		}
	}

}
