package com.sharingif.cube.core.request;

import java.util.Locale;

/**
 * 请求信息
 * 2015年7月13日 下午10:51:09
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class RequestContext<T> {
	
	private String mediaType;
	private String lookupPath;
	private Locale locale;
	private String method;
	private T request;
	
	public String getMediaType() {
		return mediaType;
	}
	public String getLookupPath() {
		return lookupPath;
	}
	public Locale getLocale() {
		if(locale == null)
			locale = Locale.getDefault();
		
		return locale;
	}
	public String getMethod() {
		return method;
	}
	public T getRequest() {
		return request;
	}
	
	public RequestContext(String mediaType, String lookupPath, Locale locale, String method, T request) {
		if(null != mediaType) {
			mediaType = mediaType.toLowerCase();
		}
		if(null != method) {
			method = method.toUpperCase();
		}
		
		this.mediaType = mediaType;
		this.lookupPath = lookupPath;
		this.locale = locale;
		this.method = method;
		this.request = request;
	}

}
