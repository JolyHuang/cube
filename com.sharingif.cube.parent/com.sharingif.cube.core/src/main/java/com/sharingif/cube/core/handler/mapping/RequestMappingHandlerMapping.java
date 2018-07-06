package com.sharingif.cube.core.handler.mapping;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringValueResolver;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.request.PatternsRequestCondition;
import com.sharingif.cube.core.handler.request.RequestCondition;
import com.sharingif.cube.core.handler.request.RequestMappingInfo;
import com.sharingif.cube.core.handler.request.RequestMethodsRequestCondition;

/**
 * RequestMappingHandlerMapping
 * 2015年6月30日 下午10:31:40
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class RequestMappingHandlerMapping extends RequestMappingInfoHandlerMapping implements EmbeddedValueResolverAware {
	
	private boolean useSuffixPatternMatch = true;

	private boolean useRegisteredSuffixPatternMatch = false;

	private boolean useTrailingSlashMatch = true;
	
	private final List<String> fileExtensions = new ArrayList<String>();
	
	private StringValueResolver embeddedValueResolver;
	
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.embeddedValueResolver  = resolver;
	}
	
	public void setUseSuffixPatternMatch(boolean useSuffixPatternMatch) {
		this.useSuffixPatternMatch = useSuffixPatternMatch;
	}
	
	public boolean useSuffixPatternMatch() {
		return this.useSuffixPatternMatch;
	}
	
	public void setUseRegisteredSuffixPatternMatch(boolean useRegsiteredSuffixPatternMatch) {
		this.useRegisteredSuffixPatternMatch = useRegsiteredSuffixPatternMatch;
		this.useSuffixPatternMatch = useRegsiteredSuffixPatternMatch ? true : this.useSuffixPatternMatch;
	}
	
	public boolean useRegisteredSuffixPatternMatch() {
		return useRegisteredSuffixPatternMatch;
	}
	
	public void setUseTrailingSlashMatch(boolean useTrailingSlashMatch) {
		this.useTrailingSlashMatch = useTrailingSlashMatch;
	}
	
	public boolean useTrailingSlashMatch() {
		return this.useTrailingSlashMatch;
	}
	
	public List<String> getFileExtensions() {
		return fileExtensions;
	}

	@Override
	protected boolean isHandler(Class<?> beanType) {
		return ((AnnotationUtils.findAnnotation(beanType, Controller.class) != null) ||
				(AnnotationUtils.findAnnotation(beanType, RequestMapping.class) != null));
	}
	
	protected RequestCondition<?> getCustomMethodCondition(Method method) {
		return null;
	}
	
	protected String[] resolveEmbeddedValuesInPatterns(String[] patterns) {
		if (this.embeddedValueResolver == null) {
			return patterns;
		}
		else {
			String[] resolvedPatterns = new String[patterns.length];
			for (int i=0; i < patterns.length; i++) {
				resolvedPatterns[i] = this.embeddedValueResolver.resolveStringValue(patterns[i]);
			}
			return resolvedPatterns;
		}
	}
	
	protected RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, RequestCondition<?> customCondition) {

		String[] patterns = resolveEmbeddedValuesInPatterns(annotation.value());
		return new RequestMappingInfo(
				annotation.name(),
				new PatternsRequestCondition(patterns, getPathMatcher(),
						this.useSuffixPatternMatch, this.useTrailingSlashMatch, this.fileExtensions),
				new RequestMethodsRequestCondition(annotation.method())
				);
	}
	
	protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
		return null;
	}

	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = null;

		if(handlerType.getName().startsWith("com.sun.proxy")) {
			return info;
		}

		RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		if (methodAnnotation != null) {
			RequestCondition<?> methodCondition = getCustomMethodCondition(method);
			info = createRequestMappingInfo(methodAnnotation, methodCondition);
			RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
			if (typeAnnotation != null) {
				RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
				info = createRequestMappingInfo(typeAnnotation, typeCondition).combine(info);
			}
		}
		return info;
	}

}
