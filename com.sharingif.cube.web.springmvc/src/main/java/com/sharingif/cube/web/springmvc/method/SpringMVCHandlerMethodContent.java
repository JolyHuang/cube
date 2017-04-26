package com.sharingif.cube.web.springmvc.method;

import java.lang.reflect.Method;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpResponse;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;

/**
 * SpringMVC HandlerMethodContent
 * 2015年8月3日 上午12:06:01
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SpringMVCHandlerMethodContent extends HttpHandlerMethodContent {

	public SpringMVCHandlerMethodContent(
			Object obj
			,Method method
			,Object[] args
			,Object returnValue
			,MethodParameter[] parameters
			,Locale locale
			,RequestInfo<?> requestInfo
			,NativeWebRequest nativeWebRequest
			,ModelAndViewContainer mavContainer
			,Object[] providedArgs
			) {
		super(obj, method, args, returnValue, parameters, locale, requestInfo, new SpringMVCHttpRequest(nativeWebRequest.getNativeRequest(HttpServletRequest.class)), new SpringMVCHttpResponse(nativeWebRequest.getNativeResponse(HttpServletResponse.class)));
		
		
		this.nativeWebRequest = nativeWebRequest;
		this.mavContainer = mavContainer;
		this.providedArgs = providedArgs;
	}
	
	private NativeWebRequest nativeWebRequest;
	private ModelAndViewContainer mavContainer;
	private Object[] providedArgs;
	
	public NativeWebRequest getNativeWebRequest() {
		return nativeWebRequest;
	}
	public void setNativeWebRequest(NativeWebRequest nativeWebRequest) {
		this.nativeWebRequest = nativeWebRequest;
	}
	public ModelAndViewContainer getMavContainer() {
		return mavContainer;
	}
	public void setMavContainer(ModelAndViewContainer mavContainer) {
		this.mavContainer = mavContainer;
	}
	public Object[] getProvidedArgs() {
		return providedArgs;
	}
	public void setProvidedArgs(Object[] providedArgs) {
		this.providedArgs = providedArgs;
	}
	
	@Override
	public void addReturnValue(String key, Object value){
		if(ModelAndView.class.isInstance(super.getReturnValue())){
			ModelAndView modelAndView = (ModelAndView)super.getReturnValue();
			modelAndView.addObject(key, value);
		}
	}
	
	@Override
	public void setViewName(String viewName){
		if(ModelAndView.class.isInstance(super.getReturnValue())){
			ModelAndView modelAndView = (ModelAndView)super.getReturnValue();
			modelAndView.setViewName(viewName);
		}
	}
	
}
