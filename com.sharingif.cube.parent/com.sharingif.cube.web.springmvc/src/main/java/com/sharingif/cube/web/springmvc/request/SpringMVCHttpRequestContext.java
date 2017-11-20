package com.sharingif.cube.web.springmvc.request;

import java.util.Locale;

import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpResponse;

/**
 * SpringMVCHttpRequestContext
 * 2017年7月28日 上午12:45:32
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SpringMVCHttpRequestContext extends HttpRequestContext<SpringMVCHttpRequest, SpringMVCHttpResponse> {

	public SpringMVCHttpRequestContext(String mediaType, String lookupPath, Locale locale, String method,
			SpringMVCHttpRequest request, SpringMVCHttpResponse response) {
		super(mediaType, lookupPath, locale, method, request, response);
	}

}
