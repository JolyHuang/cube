package com.sharingif.cube.core.handler.method;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.adapter.DefaultMappingHandlerAdapter;
import com.sharingif.cube.core.handler.adapter.MapRequestResolver;
import com.sharingif.cube.core.handler.mapping.RequestMappingHandlerMapping;
import com.sharingif.cube.core.handler.request.RequestLocalContextHolder;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * TODO description
 * 2015年8月1日 下午5:50:58
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="/com/sharingif/cube/core/handler/method/SpringContext.xml") 
public class HandlerMethodChainTest {
	
	@Resource
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	@Resource
	private MapRequestResolver mapRequestResolver;
	@Resource
	private DefaultMappingHandlerAdapter defaultMappingHandlerAdapter;
	@Resource
	private DefaultMappingHandlerAdapter exceptionMappingHandlerAdapter;
	
	@Test
	public void testHandlerMethodChain() throws CubeException{
		
		RequestLocalContextHolder.init();
		
		RequestInfo<Map<String,String>> requestInfo = mapRequestResolver.resolveRequest("/user/add");
		
		HandlerMethod handlerMethod = requestMappingHandlerMapping.getHandler(requestInfo);
		
		defaultMappingHandlerAdapter.handle(requestInfo, handlerMethod);
		
		RequestLocalContextHolder.clearContext();
	}
	
//	@Test
//	public void testHandlerMethodChainException() throws CubeException{
//		
//		RequestLocalContextHolder.init();
//		
//		RequestInfo<Map<String,String>> requestInfo = mapRequestResolver.resolveRequest("/user/add");
//		
//		HandlerMethod handlerMethod = requestMappingHandlerMapping.getHandler(requestInfo);
//		
//		exceptionMappingHandlerAdapter.handle(requestInfo, handlerMethod);
//		
//		RequestLocalContextHolder.clearContext();
//	}

}
