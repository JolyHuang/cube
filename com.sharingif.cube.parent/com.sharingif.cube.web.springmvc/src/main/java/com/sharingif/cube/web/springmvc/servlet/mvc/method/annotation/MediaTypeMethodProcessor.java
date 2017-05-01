package com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

/**
 * 通过MediaType解析方法参数
 * 2015年8月19日 下午11:04:25
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MediaTypeMethodProcessor implements HandlerMethodArgumentResolver{
	
	private final boolean annotationNotRequired;
	private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;
	private ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor;
	
	public MediaTypeMethodProcessor(
			boolean annotationNotRequired,
			RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor,
			ServletModelAttributeMethodProcessor servletModelAttributeMethodProcessor){
		this.annotationNotRequired = annotationNotRequired;
		this.requestResponseBodyMethodProcessor=requestResponseBodyMethodProcessor;
		this.servletModelAttributeMethodProcessor=servletModelAttributeMethodProcessor;
		
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(com.sharingif.cube.core.handler.bind.annotation.MediaType.class)) {
			return true;
		}
		else if (this.annotationNotRequired) {
			return !BeanUtils.isSimpleProperty(parameter.getParameterType());
		}
		else {
			return false;
		}
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,ModelAndViewContainer mavContainer, NativeWebRequest webRequest,WebDataBinderFactory binderFactory) throws Exception {
		final HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpInputMessage inputMessage = new ServletServerHttpRequest(servletRequest);
		MediaType candidateContentType = inputMessage.getHeaders().getContentType();
		if(MediaType.APPLICATION_JSON.isCompatibleWith(candidateContentType)
			|| (MediaType.APPLICATION_XML.isCompatibleWith(candidateContentType))
			|| (MediaType.APPLICATION_ATOM_XML.isCompatibleWith(candidateContentType))
				){
			return requestResponseBodyMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
		}
		
		return servletModelAttributeMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
	}

}
