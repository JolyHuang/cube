package com.sharingif.cube.web.springmvc.handler.annotation;

import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.web.springmvc.handler.SpringMVCHandlerMethodContent;
import com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation.MediaTypeMethodProcessor;
import com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation.container.DataContainerMethodProcessor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展RequestMappingHandlerAdapter，添加HandlerMethodChain、MediaTypeMethodProcessor、DataContainerMethodProcessor功能
 * 2017年4月30日 下午3:10:10
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExtendedRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {
	
	private HandlerMethodChain<SpringMVCHandlerMethodContent> handlerMethodChain;
	
	public HandlerMethodChain<SpringMVCHandlerMethodContent> getHandlerMethodChain() {
		return handlerMethodChain;
	}
	public void setHandlerMethodChain(HandlerMethodChain<SpringMVCHandlerMethodContent> handlerMethodChain) {
		this.handlerMethodChain = handlerMethodChain;
	}

	@Override
	protected ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
		ExtendedServletInvocableHandlerMethod requestMethod = new ExtendedServletInvocableHandlerMethod(handlerMethod);
		requestMethod.setHandlerMethodChain(handlerMethodChain);
		
		return requestMethod;
	}
	
	@Override
	public void afterPropertiesSet() {
		List<HandlerMethodArgumentResolver> resolvers = this.getCustomArgumentResolvers();
		
		if(null == resolvers) {
			resolvers = new ArrayList<HandlerMethodArgumentResolver>();
		}

		resolvers.add(new MediaTypeMethodProcessor(
				true,
				new RequestResponseBodyMethodProcessor(getMessageConverters()),
				new ServletModelAttributeMethodProcessor(false))
		);
		
		this.setCustomArgumentResolvers(resolvers);
		
		super.afterPropertiesSet();

		this.getArgumentResolvers().add(0,new DataContainerMethodProcessor());
	}
	
	
	
}
