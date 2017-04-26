package com.sharingif.cube.core.handler.mapping;

import org.springframework.stereotype.Component;

import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.request.RequestInfoResolver;

/*
 * TODO description
 * 2015年7月13日 下午11:17:31
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Component
public class SimpleRequestResolver implements RequestInfoResolver<String,String> {

	@Override
	public RequestInfo<String> resolveRequest(String request) {
		RequestInfo<String> requestInfo = new RequestInfo<String>(null,request,null,null,request);
		
		return requestInfo;
	}

}
