package com.sharingif.cube.web.springmvc.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.sharingif.cube.core.exception.handler.AbstractCubeExceptionHandler;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.exception.handler.WebExceptionContent;
import com.sharingif.cube.web.exception.handler.WebRequestInfo;

/**
 * SimpleHandlerExceptionResolver
 * 2015年8月6日 下午9:50:10
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SimpleHandlerExceptionResolver extends AbstractHandlerExceptionResolver {
	
	private String exceptionAttribute="_exception";
	private AbstractCubeExceptionHandler<WebRequestInfo, WebExceptionContent, HandlerMethod> cubeExceptionHandler;
	
	public AbstractCubeExceptionHandler<WebRequestInfo, WebExceptionContent, HandlerMethod> getCubeExceptionHandler() {
		return cubeExceptionHandler;
	}
	public void setCubeExceptionHandler(AbstractCubeExceptionHandler<WebRequestInfo, WebExceptionContent, HandlerMethod> cubeExceptionHandler) {
		this.cubeExceptionHandler = cubeExceptionHandler;
	}
	public String getExceptionAttribute() {
		return exceptionAttribute;
	}
	public void setExceptionAttribute(String exceptionAttribute) {
		this.exceptionAttribute = exceptionAttribute;
	}
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		
		WebRequestInfo webRequestInfo = new WebRequestInfo(request,response);
		RequestInfo<WebRequestInfo> requestInfo = new RequestInfo<WebRequestInfo>(null,null,RequestContextUtils.getLocale(request),null,webRequestInfo);
				
		org.springframework.web.method.HandlerMethod hm = (org.springframework.web.method.HandlerMethod)handler;
		
		WebExceptionContent outContent = cubeExceptionHandler.handler(requestInfo, new HandlerMethod(hm.getBean(), hm.getMethod()), ex);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(outContent.getViewName());
		modelAndView.addObject(exceptionAttribute, outContent.getCubeException());
		modelAndView.addAllObjects(outContent.getModel());
		
		return modelAndView;
	}
	
}
