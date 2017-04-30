package com.sharingif.cube.web.springmvc.method.annotation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import com.sharingif.cube.core.method.HandlerMethodContent;
import com.sharingif.cube.core.method.chain.HandlerMethodChain;
import com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation.MediaTypeMethodProcessor;
import com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation.container.DataContainerMethodProcessor;

/**
 * 扩展RequestMappingHandlerAdapter，添加HandlerMethodChain、MediaTypeMethodProcessor、DataContainerMethodProcessor功能
 * 2017年4月30日 下午3:10:10
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExtendedRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {
	
	private HandlerMethodChain<HandlerMethodContent> handlerMethodChain;
	
	public HandlerMethodChain<HandlerMethodContent> getHandlerMethodChain() {
		return handlerMethodChain;
	}
	public void setHandlerMethodChain(HandlerMethodChain<HandlerMethodContent> handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}

	public ExtendedRequestMappingHandlerAdapter() {
		super();
		
		List<HandlerMethodArgumentResolver> resolvers = new ArrayList<HandlerMethodArgumentResolver>();
		resolvers.add(new DataContainerMethodProcessor());
		
		resolvers.add(new MediaTypeMethodProcessor(
				true,
				new RequestResponseBodyMethodProcessor(getMessageConverters()),
				new ServletModelAttributeMethodProcessor(false))
		);
		this.setCustomArgumentResolvers(resolvers);
	}
	
	@Override
	protected ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
		ExtendedServletInvocableHandlerMethod requestMethod = new ExtendedServletInvocableHandlerMethod(handlerMethod);
		requestMethod.setHandlerMethodChain(handlerMethodChain);
		
		return requestMethod;
	}
	
}
