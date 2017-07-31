package com.sharingif.cube.web.exception.handler;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestInfo;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.UnknownCubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.exception.handler.AbstractCubeHandlerMethodExceptionHandler;

/**
 * CubeExceptionHandler
 * 2015年8月8日 上午11:32:28
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class WebCubeExceptionHandler extends AbstractCubeHandlerMethodExceptionHandler<HttpRequestInfo<HttpRequest, HttpResponse>> {
	
	private String defaultErrorView="DefaultExceptionView";
	
	public String getDefaultErrorView() {
		return defaultErrorView;
	}
	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	@Override
	public boolean supports(Exception exception) {
		return true;
	}

	/**
	 * 非ICubeException异常转换为UnknownCubeException
	 * @param exception : 异常
	 */
	@Override
	protected ICubeException convertExceptionInternal(Exception exception) {
		if(exception instanceof ICubeException)
			return (ICubeException)exception;
			
		return new UnknownCubeException(exception);
	}
	
	@Override
	public ExceptionContent handlerException(HttpRequestInfo<HttpRequest, HttpResponse> requestInfo, HandlerMethod handlerMethod, ICubeException cubeException) {

		ExceptionContent out = new ExceptionContent();
		out.setViewName(defaultErrorView);
		
		return out;
	}

}
