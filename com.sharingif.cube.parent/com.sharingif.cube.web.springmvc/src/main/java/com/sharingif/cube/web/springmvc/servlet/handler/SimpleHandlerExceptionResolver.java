package com.sharingif.cube.web.springmvc.servlet.handler;

import com.sharingif.cube.core.exception.handler.AbstractCubeExceptionHandler;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpResponse;
import com.sharingif.cube.web.springmvc.request.SpringMVCHttpRequestInfo;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * SimpleHandlerExceptionResolver
 * 2015年8月6日 下午9:50:10
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SimpleHandlerExceptionResolver extends AbstractHandlerExceptionResolver {
	
	private String exceptionAttribute="_exception";
	private AbstractCubeExceptionHandler<SpringMVCHttpRequestInfo, HandlerMethod> cubeExceptionHandler;
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	public AbstractCubeExceptionHandler<SpringMVCHttpRequestInfo, HandlerMethod> getCubeExceptionHandler() {
		return cubeExceptionHandler;
	}
	public void setCubeExceptionHandler(AbstractCubeExceptionHandler<SpringMVCHttpRequestInfo, HandlerMethod> cubeExceptionHandler) {
		this.cubeExceptionHandler = cubeExceptionHandler;
	}
	public String getExceptionAttribute() {
		return exceptionAttribute;
	}
	public void setExceptionAttribute(String exceptionAttribute) {
		this.exceptionAttribute = exceptionAttribute;
	}
	
	protected SpringMVCHttpRequestInfo getRequestInfo(HttpServletRequest request, HttpServletResponse response) {

		String lookupPath = urlPathHelper.getLookupPathForRequest(request);
		String method = request.getMethod().toUpperCase(Locale.ENGLISH);


		SpringMVCHttpRequestInfo requestInfo = new SpringMVCHttpRequestInfo(
				null
				,lookupPath
				,RequestContextUtils.getLocale(request)
				,method
				,new SpringMVCHttpRequest(request)
				,new SpringMVCHttpResponse(response));

		return requestInfo;

	}
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		SpringMVCHttpRequestInfo requestInfo = getRequestInfo(request, response);
				
		HandlerMethod handlerMethod = null;
		org.springframework.web.method.HandlerMethod hm = (org.springframework.web.method.HandlerMethod)handler;
		if(hm != null) {
			handlerMethod = new HandlerMethod(hm.getBean(), hm.getMethod());
		}

		ExceptionContent outContent = cubeExceptionHandler.handler(requestInfo, handlerMethod, ex);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(outContent.getViewName());
		modelAndView.addObject(exceptionAttribute, outContent.getCubeException());
		modelAndView.addAllObjects(outContent.getModel());
		
		return modelAndView;
	}
	
}
