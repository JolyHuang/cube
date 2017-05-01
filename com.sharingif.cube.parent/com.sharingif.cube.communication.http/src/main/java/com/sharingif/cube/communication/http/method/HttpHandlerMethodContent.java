package com.sharingif.cube.communication.http.method;

import java.lang.reflect.Method;
import java.util.Locale;

import org.springframework.core.MethodParameter;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * web HandlerMethodContent
 * 2015年8月3日 上午12:06:01
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpHandlerMethodContent extends HandlerMethodContent {

	public HttpHandlerMethodContent(
			Object obj
			,Method method
			,Object[] args
			,Object returnValue
			,MethodParameter[] parameters
			,Locale locale
			,RequestInfo<?> requestInfo
			,HttpRequest request
			,HttpResponse response
			) {
		super(obj, method, args, returnValue, parameters, locale, requestInfo);
		
		this.request = request;
		this.response = response;
	}
	
	private HttpRequest request;
	private HttpResponse response;

	public HttpRequest getRequest() {
		return request;
	}
	public void setRequest(HttpRequest request) {
		this.request = request;
	}
	public HttpResponse getResponse() {
		return response;
	}
	public void setResponse(HttpResponse response) {
		this.response = response;
	}
	
}
