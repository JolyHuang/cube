package com.sharingif.cube.core.handler.chain;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HandlerMethodContent {
	
	public HandlerMethodContent(
			HandlerMethod handlerMethod
			,Object[] args
			,Object returnValue
			,Locale locale
			,RequestInfo<?> requestInfo
	) {
		this.handlerMethod = handlerMethod;
		this.args = args;
		this.returnValue = returnValue;
		this.locale = locale;
		this.requestInfo = requestInfo;
	}

	private HandlerMethod handlerMethod;
	private Object args[];
	private Object returnValue;
	private Locale locale;
	private RequestInfo<?> requestInfo;

	private String viewName;


	private Map<String,Object> cacheDataMap = new HashMap<String,Object>();

	public HandlerMethod getHandlerMethod() {
		return handlerMethod;
	}
	public Object[] getArgs() {
		return args;
	}
	public Object getReturnValue() {
		return returnValue;
	}
	public Locale getLocale() {
		return locale;
	}
	
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	@SuppressWarnings("unchecked")
	public <T extends RequestInfo<?>> T getRequestInfo() {
		return (T)requestInfo;
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

	public void addacheData(String key, Object obj) {
		cacheDataMap.put(key,obj);
	}
	@SuppressWarnings("unchecked")
	public <T> T getCacheData(String key) {
		return (T)cacheDataMap .get(key);
	}

}
