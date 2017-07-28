package com.sharingif.cube.communication.http.request;

import java.util.Locale;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * 处理http请求,增加HttpRequest属性
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/7/27 下午10:43
 */
public class HttpRequestInfo<Req extends HttpRequest, Rsp extends HttpResponse> extends RequestInfo<Req> {

	private Rsp response;
	
    public HttpRequestInfo(String mediaType, String lookupPath, Locale locale, String method, Req request, Rsp response) {
        super(mediaType, lookupPath, locale, method, request);
        
        this.response = response;
    }

	public Rsp getResponse() {
		return response;
	}
    
}
