package com.sharingif.cube.web.springmvc.handler.annotation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.web.springmvc.method.annotation.ExtendedRequestParamMethodArgumentResolver;
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
	
	private HandlerMethodChain handlerMethodChain;
	
	public HandlerMethodChain getHandlerMethodChain() {
		return handlerMethodChain;
	}
	public void setHandlerMethodChain(HandlerMethodChain handlerMethodChain) {
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
		List<HandlerMethodArgumentResolver> customArgumentResolvers = this.getCustomArgumentResolvers();
		
		if(null == customArgumentResolvers) {
			customArgumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();

			customArgumentResolvers.add(new MediaTypeMethodProcessor(
					true,
					new RequestResponseBodyMethodProcessor(getMessageConverters()),
					new ServletModelAttributeMethodProcessor(false))
			);

			this.setCustomArgumentResolvers(customArgumentResolvers);
		}

		super.afterPropertiesSet();

		List<HandlerMethodArgumentResolver> argumentResolvers = this.getArgumentResolvers();
		List<HandlerMethodArgumentResolver> newArgumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
		newArgumentResolvers.add(new ExtendedRequestParamMethodArgumentResolver(getBeanFactory(),false));
		newArgumentResolvers.add(new DataContainerMethodProcessor());

		for(HandlerMethodArgumentResolver handlerMethodArgumentResolver : argumentResolvers) {
			newArgumentResolvers.add(handlerMethodArgumentResolver);
		}
		this.setArgumentResolvers(newArgumentResolvers);


		List<HandlerMethodReturnValueHandler> returnValueHandlers = this.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> newReturnValueHandlers = new ArrayList<HandlerMethodReturnValueHandler>();

		for(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler : returnValueHandlers) {
			newReturnValueHandlers.add(handlerMethodReturnValueHandler);
		}

		this.setReturnValueHandlers(newReturnValueHandlers);
	}
	
	
	
}
