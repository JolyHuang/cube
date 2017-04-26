package com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation.container;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;


/**   
 *  
 * @Description:  [解析DataContainer注解方法参数]   
 * @Author:       [Joly]   
 * @CreateDate:   [2015年3月31日 下午11:00:44]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2015年3月31日 下午11:00:44]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public interface DataContainerMethodArgumentResolver {
	
	boolean supportsParameter(Class<?> parameterType);
	
	Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception;

}
