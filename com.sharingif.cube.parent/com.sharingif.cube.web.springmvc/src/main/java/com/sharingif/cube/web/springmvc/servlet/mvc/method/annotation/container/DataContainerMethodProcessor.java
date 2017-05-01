package com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation.container;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sharingif.cube.core.handler.bind.annotation.DataContainer;

/**   
 *  
 * @Description:  [解析DataContainer注解方法参数]   
 * @Author:       [Joly]   
 * @CreateDate:   [2015年2月8日 下午2:08:13]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2015年2月8日 下午2:08:13]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class DataContainerMethodProcessor implements HandlerMethodArgumentResolver {
	
	private List<DataContainerMethodArgumentResolver> dataContainerMethodArgumentResolvers;
	
	protected List<DataContainerMethodArgumentResolver> getDataContainerMethodArgumentResolvers() {
		return dataContainerMethodArgumentResolvers;
	}
	public void setDataContainerMethodArgumentResolvers(List<DataContainerMethodArgumentResolver> dataContainerMethodArgumentResolvers) {
		this.dataContainerMethodArgumentResolvers = dataContainerMethodArgumentResolvers;
	}

	public DataContainerMethodProcessor() {
		dataContainerMethodArgumentResolvers = new ArrayList<DataContainerMethodArgumentResolver>();
		
		dataContainerMethodArgumentResolvers.add(new CoreUserDataContainerMethodArgumentResolver());
		dataContainerMethodArgumentResolvers.add(new HttpChannelContextDataContainerMethodArgumentResolver());
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(DataContainer.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		for(DataContainerMethodArgumentResolver dataContainerMethodArgumentResolver : dataContainerMethodArgumentResolvers){
			if(dataContainerMethodArgumentResolver.supportsParameter(parameter.getParameterType())){
				return dataContainerMethodArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
			}
		}
		return null;
	}

}
