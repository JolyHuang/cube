package com.sharingif.cube.communication.http.transport;

import java.util.Locale;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;

import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.http.HttpMethod;
import com.sharingif.cube.core.method.bind.annotation.PathVariable;
import com.sharingif.cube.core.method.bind.annotation.RequestMapping;
import com.sharingif.cube.core.method.bind.annotation.RequestMethod;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.request.RequestInfoResolver;
import com.sharingif.cube.core.util.StringUtils;

/**
 * HttpJsonRequestInfoResolver
 * 2017年1月9日 下午9:23:05
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpJsonRequestInfoResolver implements RequestInfoResolver<Object[], Object[]> {

	@Override
	public RequestInfo<Object[]> resolveRequest(Object[] request) {
		
		HttpJsonRemoteHandlerMethodTransport remoteHttpJsonHandlerMethod = (HttpJsonRemoteHandlerMethodTransport) request[0];
		Object[] args = (Object[])request[1];
		
		RequestMapping beanRequestMapping = AnnotationUtils.findAnnotation(remoteHttpJsonHandlerMethod.getBean().getClass(), RequestMapping.class);
		RequestMapping methodRequestMapping = AnnotationUtils.findAnnotation(remoteHttpJsonHandlerMethod.getMethod(), RequestMapping.class);
		RequestMethod bestRequestMethod = methodRequestMapping.method()[0];
		
		String bestBeanValue = beanRequestMapping.value()[0];
		String bestMethodValue = methodRequestMapping.value()[0];
		
		StringBuilder lookupPathStringBuilder = new StringBuilder();
		
		if(!bestBeanValue.startsWith("/")) {
			lookupPathStringBuilder.append("/");
		}
		lookupPathStringBuilder.append(bestBeanValue);
		
		if(!bestBeanValue.endsWith("/") && !bestMethodValue.startsWith("/")){
			lookupPathStringBuilder.append("/");
		}
		lookupPathStringBuilder.append(bestMethodValue);
		
		String lookupPath = lookupPathStringBuilder.toString();
		if(HttpMethod.GET.name().equals(bestRequestMethod.name()) && (null != args)) {
			MethodParameter[] parameters = remoteHttpJsonHandlerMethod.getMethodParameters();
			for(int i=0; i<parameters.length; i++) {
				MethodParameter parameter = parameters[i];
				if(!parameter.getClass().isPrimitive() && !(parameter.getParameterType().isAssignableFrom(String.class))){
					continue;
				}
				PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
				parameter.initParameterNameDiscovery(remoteHttpJsonHandlerMethod.getParameterNameDiscoverer());
				String name = parameter.getParameterName();
				if(pathVariable != null && !StringUtils.isEmpty(pathVariable.value())) {
					name = pathVariable.value();
				}
				lookupPath = lookupPath.replace((new StringBuffer("{").append(name).append("}").toString()), String.valueOf(args[i]));
			}
		}
		
		RequestInfo<Object[]> requestInfo = new RequestInfo<Object[]>(MediaType.APPLICATION_JSON.toString(), lookupPath, Locale.getDefault(), bestRequestMethod.name(), args);
		
		return requestInfo;
	}

}
