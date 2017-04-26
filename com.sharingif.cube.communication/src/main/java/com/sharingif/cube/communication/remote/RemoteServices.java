package com.sharingif.cube.communication.remote;

import java.beans.Introspector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ClassUtils;

import com.sharingif.cube.communication.transport.AbstractRemoteHandlerMethodTransport;
import com.sharingif.cube.core.method.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.request.RequestInfoResolver;

/**
 * 远程服务
 * 2017年1月9日 下午8:32:20
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class RemoteServices {
	
	private RequestInfoResolver<Object[], ?> requestInfoResolver;
	private RemoteHandlerMethodTransportFactory<?,?,?,?,?,?,?> remoteHandlerMethodTransportFactory;
	private List<String> services;
	
	private Map<String, AbstractRemoteHandlerMethodTransport<?,?,?,?,?,?,?>> cacheAbstractRemoteHandlerMethodTransportMap = new HashMap<String,AbstractRemoteHandlerMethodTransport<?,?,?,?,?,?,?>>(128);
	
	public RequestInfoResolver<Object[], ?> getRequestInfoResolver() {
		return requestInfoResolver;
	}
	public void setRequestInfoResolver(RequestInfoResolver<Object[], ?> requestInfoResolver) {
		this.requestInfoResolver = requestInfoResolver;
	}
	public void setRemoteHandlerMethodTransportFactory(RemoteHandlerMethodTransportFactory<?,?,?,?,?,?,?> remoteHandlerMethodTransportFactory) {
		this.remoteHandlerMethodTransportFactory = remoteHandlerMethodTransportFactory;
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
					AbstractRemoteHandlerMethodTransport remoteHandlerMethodTransport = (AbstractRemoteHandlerMethodTransport) cacheAbstractRemoteHandlerMethodTransportMap.get(key);
					if(remoteHandlerMethodTransport == null) {
						remoteHandlerMethodTransport = remoteHandlerMethodTransportFactory.createRemoteHandlerMethodTransport(proxy, method);
						if(null == remoteHandlerMethodTransport.getDataBinderFactory()){
							remoteHandlerMethodTransport.setDataBinderFactory(dataBinderFactory);
						}
						cacheAbstractRemoteHandlerMethodTransportMap.put(key, remoteHandlerMethodTransport);
					}
					
					RequestInfo<?> requestInfo = requestInfoResolver.resolveRequest(new Object[]{remoteHandlerMethodTransport, args});
					
					return remoteHandlerMethodTransport.doTransport(requestInfo);
				}
				
			});
			
			proxyClassMap.put(proxyClassName, proxyClass);
			
		}
		
		return proxyClassMap;
	}
	
}
