package com.sharingif.cube.core.method;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import org.springframework.core.MethodParameter;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.request.RequestInfo;

public class HandlerMethodContent {
	
	
	
	public HandlerMethodContent(
			Object obj
			,Method method
			,Object[] args
			,Object returnValue
			,MethodParameter[] parameters
			,Locale locale
			,RequestInfo<?> requestInfo
	) {
		this.obj = obj;
		this.method = method;
		this.args = args;
		this.returnValue = returnValue;
		this.parameters = parameters;
		this.locale = locale;
		this.requestInfo = requestInfo;
	}
	
	private Object obj;
	private Method method;
	private Object args[];
	private Object returnValue;
	private MethodParameter[] parameters;
	private Locale locale;
	private RequestInfo<?> requestInfo;
	
	private String viewName;
	
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public Object getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	public MethodParameter[] getParameters() {
		return parameters;
	}
	public void setParameters(MethodParameter[] parameters) {
		this.parameters = parameters;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public RequestInfo<?> getRequestInfo() {
		return requestInfo;
	}
	public void setRequestInfo(RequestInfo<?> requestInfo) {
		this.requestInfo = requestInfo;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T findObject(Class<T> target){
		for(Object obj : args){
			if(target.isInstance(obj)){
				return (T)obj;
			}
		}
		return null;
	}

	public <T> T getObject(Class<T> target){
		T obj = findObject(target);
		
		if(null == obj)
			throw new ValidationCubeException(new StringBuilder(target.getSimpleName()).append(" is null").toString());
		
		return obj;
	}
	
	/**
	 * 更改调用方法值
	 * @param obj
	 * @param content
	 */
	public void changeArgsValue(Object obj) {
		for(int i = 0; i<args.length; i++){
			Class<?> argsClass = (args[i]).getClass();
			if(argsClass.isInstance(obj) || obj.getClass().isAssignableFrom(argsClass)){
				args[i] = obj;
				return;
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addReturnValue(String key, Object value){
		if(null == returnValue)
			return;
		
		if(Map.class.isInstance(returnValue)){
			((Map)returnValue).put(key, value);
		}
	}
	
	public void setViewName(String viewName){
		this.viewName = viewName;
	}
	public String getViewName() {
		return viewName;
	}
	

}
