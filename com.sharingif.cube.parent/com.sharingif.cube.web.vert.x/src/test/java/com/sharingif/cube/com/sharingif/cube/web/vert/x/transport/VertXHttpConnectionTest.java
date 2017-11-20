package com.sharingif.cube.com.sharingif.cube.web.vert.x.transport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Locale;

import org.junit.Test;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.http.HttpMethod;
import com.sharingif.cube.communication.http.transport.transform.HttpJsonTransform;
import com.sharingif.cube.communication.http.transport.transform.MethodParameterArgumentToJsonModelMarshaller;
import com.sharingif.cube.communication.http.transport.transform.ObjectToJsonStringMarshaller;
import com.sharingif.cube.communication.transport.ProxyInterfaceHandlerMethodCommunicationTransport;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.bind.support.ConfigurableBindingInitializer;
import com.sharingif.cube.core.handler.bind.support.DefaultDataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.web.vert.x.transport.VertXHttpJsonConnection;


/**
 * VertXHttpConnectionTest
 * 2016年12月27日 下午10:09:07
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXHttpConnectionTest {

	static VertXHttpJsonConnection vertXHttpConnection = new VertXHttpJsonConnection("localhost", 8081, "wechat-server");
	static ObjectToJsonStringMarshaller jsonMarshaller = new ObjectToJsonStringMarshaller();
	static MethodParameterArgumentToJsonModelMarshaller jsonUnmarshaller = new MethodParameterArgumentToJsonModelMarshaller();
	
	@Test
	public void testPost() {
		
		RequestContext<String> vertXHttpJsonContext = new RequestContext<String>(MediaType.APPLICATION_JSON_VALUE, "user/add", Locale.getDefault(), HttpMethod.POST.name(), "{\"name\":\"Joly\",\"age\":18,\"birthday\":1482847605601}");
		
		vertXHttpConnection.connect(vertXHttpJsonContext);
	}
	
	@Test
	public void testGet() {
		
		RequestContext<String> vertXHttpJsonContext = new RequestContext<String>(MediaType.APPLICATION_JSON_VALUE, "user/get/123/", Locale.getDefault(), HttpMethod.POST.name(), null);
		
		vertXHttpConnection.connect(vertXHttpJsonContext);
	}
	
	public static void main(String[] args) {
		
		UserService userService = (UserService) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{UserService.class}, new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				ConfigurableBindingInitializer bindingInitializer = new ConfigurableBindingInitializer();
				
				ProxyInterfaceHandlerMethodCommunicationTransport<String,String,JsonModel<Object>> proxyInterfaceHandlerMethodCommunicationTransport = new ProxyInterfaceHandlerMethodCommunicationTransport<String,String,JsonModel<Object>>(new HandlerMethod(proxy, method));
				proxyInterfaceHandlerMethodCommunicationTransport.setDataBinderFactory(new DefaultDataBinderFactory(bindingInitializer));
				
				proxyInterfaceHandlerMethodCommunicationTransport.setConnection(vertXHttpConnection);
				
				HttpJsonTransform transform = new HttpJsonTransform();
				transform.setMarshaller(jsonMarshaller);
				transform.setUnmarshaller(jsonUnmarshaller);
				
				proxyInterfaceHandlerMethodCommunicationTransport.setTransform(transform);
				
				RequestContext<Object[]> requestContext = new RequestContext<Object[]>(MediaType.APPLICATION_JSON.toString(), "user/get/123/", null, HttpMethod.GET.name(), null);
				User user = (User) proxyInterfaceHandlerMethodCommunicationTransport.doTransport(requestContext);
				System.out.println("returnValue:"+user);
				
//				User user = new User();
//				user.setName("Joly");
//				user.setAge(18);
//				user.setBirthday(new Date());
//				HttpPostJsonContext<User> vertXHttpJsonContext = new HttpPostJsonContext<User>("user/add", user);
				
				
				return null;
			}
			
		});
		
		userService.get();
		
	}
	
}
