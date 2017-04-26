package com.sharingif.cube.core.handler.mapping;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.method.HandlerMethod;
import com.sharingif.cube.core.method.handler.mapping.RequestMappingHandlerMapping;
import com.sharingif.cube.core.request.RequestInfo;


/**
 * TODO description
 * 2015年7月2日 下午9:11:44
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="/com/sharingif/cube/core/handler/mapping/SpringContext.xml") 
public class RequestMappingHandlerMappingTest {
	
	@Resource
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	@Resource
	private SimpleRequestResolver simpleRequestResolver;
	
	@Test
	public void testRequestMappingHandlerMapping() throws CubeException{
		
		RequestInfo<String> requestInfo = simpleRequestResolver.resolveRequest("/user/add");
		
		System.out.println(requestMappingHandlerMapping.getHandlerMethods());
		
		HandlerMethod handlerMethod = requestMappingHandlerMapping.getHandler(requestInfo);
		System.out.println(handlerMethod);
	}

}
