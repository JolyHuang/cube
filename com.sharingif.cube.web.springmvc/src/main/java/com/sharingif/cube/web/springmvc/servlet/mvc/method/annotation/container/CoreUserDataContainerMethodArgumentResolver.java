package com.sharingif.cube.web.springmvc.servlet.mvc.method.annotation.container;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.user.CoreUserHttpSessionManage;
import com.sharingif.cube.web.user.IWebUserManage;

/**   
 *  
 * @Description:  [处理用户]   
 * @Author:       [Joly]   
 * @CreateDate:   [2015年3月31日 下午11:12:47]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2015年3月31日 下午11:12:47]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class CoreUserDataContainerMethodArgumentResolver implements DataContainerMethodArgumentResolver {

	private IWebUserManage webUserManage;
	
	public CoreUserDataContainerMethodArgumentResolver() {
		webUserManage = new CoreUserHttpSessionManage();
	}
	
	@Override
	public boolean supportsParameter(Class<?> parameterType) {
		return ICoreUser.class.isAssignableFrom(parameterType);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return webUserManage.getUser(new SpringMVCHttpRequest(webRequest.getNativeRequest(HttpServletRequest.class)));
	}
	
	

}
