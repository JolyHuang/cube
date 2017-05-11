package com.sharingif.cube.web.vert.x.transport;

import com.sharingif.cube.communication.exception.CommunicationException;
import com.sharingif.cube.communication.http.HttpMethod;
import com.sharingif.cube.communication.transport.Connection;
import com.sharingif.cube.core.request.RequestInfo;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpHeaders;

/**
 * VertXHttpConnection
 * 2016年12月27日 下午12:39:33
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXHttpJsonConnection implements Connection<RequestInfo<String>, String> {
	
	private final Vertx vertx = Vertx.vertx();
	private final HttpClient client;
	
	public VertXHttpJsonConnection(String host, int port, String contextPath) {
		
		this.host = host;
		this.port = port;
		this.contextPath = contextPath;
		
		client = vertx.createHttpClient(
				new HttpClientOptions()
		        	.setDefaultHost(getHost())
		        	.setDefaultPort(getPort())
		        	.setKeepAlive(true) 
	                .setTcpNoDelay(true) 
	                .setConnectTimeout(10) 
		);
	}
	
	private String host;
	private int port;
	private String contextPath;
	
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
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	@Override
	public String connect(RequestInfo<String> vertXHttpContext) throws CommunicationException {
		
		return json(vertXHttpContext);
		
	}
	
	protected String json(RequestInfo<String> vertXHttpContext) {
		Buffer buffer = null;
		if(vertXHttpContext.getRequest() != null){
			buffer = Buffer.buffer(vertXHttpContext.getRequest());
		}
		
		String url = new StringBuffer("/").append(contextPath).append("/").append(vertXHttpContext.getLookupPath()).toString();
		
		HttpClientRequest request = null;
		if(vertXHttpContext.getMethod().equals(HttpMethod.GET.name())) {
			request = client.get(url);
		}
		if(vertXHttpContext.getMethod().equals(HttpMethod.POST.name())) {
			request = client.post(url);
		}
		
		request.handler(response -> {
			int statusCode = response.statusCode();
			if(HttpResponseStatus.OK.code() == statusCode) {
				response.bodyHandler(responseData -> {
//					String data = new String(responseData.getBytes());
				});
			} else {
				throw new CommunicationException(new StringBuilder("statusCode:").append(statusCode).toString());
			}
		});
		request.putHeader(HttpHeaders.CONTENT_TYPE, vertXHttpContext.getMediaType());
		if(buffer != null){
			request.putHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(buffer.length()));
			request.write(buffer);
		}
		request.end();
		
		return null;
		
	}
	
}
