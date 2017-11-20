package com.sharingif.cube.communication.remote;

import java.beans.Introspector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.sharingif.cube.communication.transport.AbstractHandlerMethodCommunicationTransport;
import com.sharingif.cube.communication.transport.AbstractHandlerMethodCommunicationTransportFactory;
import com.sharingif.cube.communication.transport.ProxyInterfaceHandlerMethodCommunicationTransport;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.request.RequestContextResolver;

/**
 * 远程服务
 * 2017年1月9日 下午8:32:20
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class RemoteServices {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private RequestContextResolver<Object[], ?> requestContextResolver;
	private AbstractHandlerMethodCommunicationTransportFactory<?,?,?,?,?,?> handlerMethodCommunicationTransportFactory;
	private List<String> services;
	
	private Map<String, AbstractHandlerMethodCommunicationTransport<?,?,?,?,?,?>> cacheHandlerMethodCommunicationTransportMap = new HashMap<String,AbstractHandlerMethodCommunicationTransport<?,?,?,?,?,?>>(128);
	
	
	public RequestContextResolver<Object[], ?> getRequestContextResolver() {
		return requestContextResolver;
	}
	public void setRequestContextResolver(RequestContextResolver<Object[], ?> requestContextResolver) {
		this.requestContextResolver = requestContextResolver;
	}
	public AbstractHandlerMethodCommunicationTransportFactory<?,?,?,?,?,?> getHandlerMethodCommunicationTransportFactory() {
		return handlerMethodCommunicationTransportFactory;
	}
	public void setHandlerMethodCommunicationTransportFactory(
			AbstractHandlerMethodCommunicationTransportFactory<?,?,?,?,?,?> handlerMethodCommunicationTransportFactory) {
		this.handlerMethodCommunicationTransportFactory = handlerMethodCommunicationTransportFactory;
	}
	public List<String> getServices() {
		return services;
	}
	public void setServices(List<String> services) {
		this.services = services;
	}
	
	public Map<String,Object> initServices(DataBinderFactory dataBinderFactory) throws ClassNotFoundException {
		
		Map<String, Object> proxyClassMap = new HashMap<String, Object>(services.size());
		
		for(String service : services) {
			
			Class<?> cls = null;
			try {
				cls = Class.forName(service);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			
			String shortClassName = ClassUtils.getShortName(cls.getName());
			
			String proxyClassName = Introspector.decapitalize(shortClassName);
			
			Object proxyClass = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{cls}, new InvocationHandler() {
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					String key = new StringBuilder(method.getDeclaringClass().getTypeName()).append('.').append(method.getName()).toString();
					AbstractHandlerMethodCommunicationTransport handlerMethodCommunicationTransport = (ProxyInterfaceHandlerMethodCommunicationTransport) cacheHandlerMethodCommunicationTransportMap.get(key);
					if(handlerMethodCommunicationTransport == null) {
						handlerMethodCommunicationTransport = handlerMethodCommunicationTransportFactory.createHandlerMethodCommunicationTransport(proxy, method);
						if(null == handlerMethodCommunicationTransport.getDataBinderFactory()){
							handlerMethodCommunicationTransport.setDataBinderFactory(dataBinderFactory);
						}
						cacheHandlerMethodCommunicationTransportMap.put(key, handlerMethodCommunicationTransport);
					}
					
					RequestContext<?> requestContext = requestContextResolver.resolveRequest(new Object[]{handlerMethodCommunicationTransport, args});
					
					return handlerMethodCommunicationTransport.doTransport(requestContext);
				}
				
			});
			
			Object oldproxyClass = proxyClassMap.put(proxyClassName, proxyClass);
			if(null != oldproxyClass) {
				this.logger.error("roxy class name repeat,name:{}",proxyClassName);
				throw new ValidationCubeException("proxy class name repeat");
			}
			
		}
		
		return proxyClassMap;
	}
	
}
