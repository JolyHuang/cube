package com.sharingif.cube.core.handler.adapter;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.mapping.RequestMappingHandlerMapping;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * TODO description
 * 2015年7月28日 下午10:41:21
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="/com/sharingif/cube/core/handler/adapter/SpringContext.xml")
public class DefaultMappingHandlerAdapterTest {
	
	@Resource
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	@Resource
	private MapRequestResolver mapRequestResolver;
	@Resource
	private DefaultMappingHandlerAdapter defaultMappingHandlerAdapter;
	
	@Test
	public void testDefaultMappingHandlerAdapter() throws CubeException{
		
		RequestInfo<Map<String,String>> requestInfo = mapRequestResolver.resolveRequest("/user/add");
		
		HandlerMethod handlerMethod = requestMappingHandlerMapping.getHandler(requestInfo);
		
		defaultMappingHandlerAdapter.handle(requestInfo, handlerMethod);
	}

}
