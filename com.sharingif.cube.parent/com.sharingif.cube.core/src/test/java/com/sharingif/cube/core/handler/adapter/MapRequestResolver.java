package com.sharingif.cube.core.handler.adapter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.request.RequestInfoResolver;

/**
 * TODO description
 * 2015年7月28日 下午10:59:49
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Component
public class MapRequestResolver implements RequestInfoResolver<String, Map<String,String>> {

	@Override
	public RequestInfo<Map<String, String>> resolveRequest(String request) {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", "Jol");
		map.put("birthday", "2015-01-01");
		map.put("age", "12");
		
		RequestInfo<Map<String,String>> requestInfo = new RequestInfo<Map<String,String>>(null,request,null,null,map);
		
		return requestInfo;
	}

}
