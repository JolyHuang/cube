package com.sharingif.cube.web.method.handler.adapter;

import java.util.Collections;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ValueConstants;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.method.bind.annotation.PathVariable;
import com.sharingif.cube.core.method.bind.annotation.RequestMapping;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * PathVariableMethodArgumentResolver
 * 2017年1月6日 上午11:00:29
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class PathVariableMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
	
	private PathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public boolean supportsParameter(MethodParameter parameter, RequestInfo<?> requestInfo) {
		if (!parameter.hasParameterAnnotation(PathVariable.class)) {
			return false;
		}
		if (Map.class.isAssignableFrom(parameter.getParameterType())) {
			String paramName = parameter.getParameterAnnotation(PathVariable.class).value();
			return StringUtils.hasText(paramName);
		}
		return true;
	}


	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		PathVariable annotation = parameter.getParameterAnnotation(PathVariable.class);
		return new PathVariableNamedValueInfo(annotation);
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, RequestInfo<?> requestInfo)
			throws CubeException {
		
		RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(parameter.getMethod(), RequestMapping.class);
		String pattern = methodAnnotation.value()[0];
		String lookupPath = requestInfo.getLookupPath();
		
		int patternNumber = pattern.split("/").length-1;
		if(!pattern.startsWith("/")) {
			patternNumber++;
			pattern = new StringBuilder("/").append(pattern).toString();
		}
		
		
		int lookupPathNumber = lookupPath.split("/").length-1;
		if(!lookupPath.startsWith("/")) {
			lookupPathNumber++;
		}
		
		for(int i=(lookupPathNumber - patternNumber); i>-1; i--) {
			lookupPath = lookupPath.substring(lookupPath.indexOf("/")+1, lookupPath.length());
		}
		lookupPath = new StringBuilder("/").append(lookupPath).toString();
		
		if(lookupPath.endsWith("/")) {
			pattern = new StringBuilder(pattern).append("/").toString();
		}
		
		Map<String, String> uriVariables = Collections.emptyMap();
		uriVariables = pathMatcher.extractUriTemplateVariables(pattern, lookupPath);
		
		return uriVariables.get(name);
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter) throws CubeException {
		throw new ValidationCubeException("Missing URI template variable '" + name +
				"' for method parameter of type " + parameter.getParameterType().getSimpleName());
		
	}
	
	private static class PathVariableNamedValueInfo extends NamedValueInfo {

		public PathVariableNamedValueInfo(PathVariable annotation) {
			super(annotation.value(), true, ValueConstants.DEFAULT_NONE);
		}
	}
	
}
