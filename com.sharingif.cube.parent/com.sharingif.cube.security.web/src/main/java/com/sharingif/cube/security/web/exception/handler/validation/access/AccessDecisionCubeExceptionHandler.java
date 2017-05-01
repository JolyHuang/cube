package com.sharingif.cube.security.web.exception.handler.validation.access;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.security.exception.validation.access.AccessDecisionCubeException;
import com.sharingif.cube.web.exception.handler.WebExceptionContent;
import com.sharingif.cube.web.exception.handler.WebRequestInfo;
import com.sharingif.cube.web.exception.handler.validation.ValidationCubeExceptionHandler;

/**
 * [访问决策异常]
 * [2015年4月10日 下午11:52:09]
 * [@author Joly]
 * [@version v1.0]
 * [@since v1.0]
 */
public class AccessDecisionCubeExceptionHandler extends ValidationCubeExceptionHandler {

	private String defaultErrorView;
	
	public String getDefaultErrorView() {
		return defaultErrorView;
	}
	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}
	
	@Override
	public boolean supports(Exception exception) {
		return exception instanceof AccessDecisionCubeException;
	}
	@Override
	public WebExceptionContent handlerException(
			RequestInfo<WebRequestInfo> requestInfo,
			HandlerMethod handlerMethod,
			ICubeException cubeException) {
		
		WebExceptionContent out = new WebExceptionContent();
		out.setViewName(defaultErrorView);
		
		return out;
	}
	
	

	
}
