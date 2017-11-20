package com.sharingif.cube.security.web.exception.handler.validation.access;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.security.exception.validation.access.AccessDecisionCubeException;
import com.sharingif.cube.web.exception.handler.validation.ValidationCubeExceptionHandler;

/**
 * [访问决策异常]
 * [2015年4月10日 下午11:52:09]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class AccessDecisionCubeExceptionHandler extends ValidationCubeExceptionHandler {

	@Override
	public boolean supports(Exception exception) {
		return exception instanceof AccessDecisionCubeException;
	}
	@Override
	public ExceptionContent handlerException(
			HttpRequestContext<HttpRequest, HttpResponse> requestContext,
			HandlerMethod handlerMethod,
			ICubeException cubeException) {

		ExceptionContent out = new ExceptionContent();
		out.setViewName(getDefaultErrorView());
		
		return out;
	}
	
	

	
}
