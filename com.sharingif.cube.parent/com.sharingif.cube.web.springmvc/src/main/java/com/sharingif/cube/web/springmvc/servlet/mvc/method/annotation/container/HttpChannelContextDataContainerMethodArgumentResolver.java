package com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation.container;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sharingif.cube.components.channel.HttpChannelContext;

/**   
 *  
 * @Description:  [处理HttpChannelContext]   
 * @Author:       [Joly]   
 * @CreateDate:   [2015年3月31日 下午11:27:36]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2015年3月31日 下午11:27:36]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class HttpChannelContextDataContainerMethodArgumentResolver implements DataContainerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(Class<?> parameterType) {
		return HttpChannelContext.class.isAssignableFrom(parameterType);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		
		HttpChannelContext httpChannelContext = new HttpChannelContext();
		httpChannelContext.setIp4(httpServletRequest.getRemoteAddr());
		
		return httpChannelContext;
	}
	
}
