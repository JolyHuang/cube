package com.sharingif.cube.core.handler.mapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringValueResolver;

import com.sharingif.cube.core.handler.AbstractRequestCondition;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.request.PatternsRequestCondition;
import com.sharingif.cube.core.handler.request.RequestCondition;
import com.sharingif.cube.core.handler.request.RequestMappingInfo;
import com.sharingif.cube.core.handler.request.RequestMethodsRequestCondition;

/**
 * Creates {@link RequestMappingInfo} instances from type and method-level
 * {@link RequestMapping @RequestMapping} annotations in
 * {@link Controller @Controller} classes.
 * 
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
	
	/**
	 * Whether to use suffix pattern match (".*") when matching patterns to
	 * requests. If enabled a method mapped to "/users" also matches to "/users.*".
	 * <p>The default value is {@code true}.
	 * <p>Also see {@link #setUseRegisteredSuffixPatternMatch(boolean)} for
	 * more fine-grained control over specific suffixes to allow.
	 */
	public void setUseSuffixPatternMatch(boolean useSuffixPatternMatch) {
		this.useSuffixPatternMatch = useSuffixPatternMatch;
	}
	
	/**
	 * Whether to use suffix pattern matching.
	 */
	public boolean useSuffixPatternMatch() {
		return this.useSuffixPatternMatch;
	}
	
	/**
	 * Whether to use suffix pattern match for registered file extensions only
	 * when matching patterns to requests.
	 *
	 * <p>If enabled, a controller method mapped to "/users" also matches to
	 * "/users.json" assuming ".json" is a file extension registered with the
	 * provided {@link #setContentNegotiationManager(ContentNegotiationManager)
	 * contentNegotiationManager}. This can be useful for allowing only specific
	 * URL extensions to be used as well as in cases where a "." in the URL path
	 * can lead to ambiguous interpretation of path variable content, (e.g. given
	 * "/users/{user}" and incoming URLs such as "/users/john.j.joe" and
	 * "/users/john.j.joe.json").
	 *
	 * <p>If enabled, this flag also enables
	 * {@link #setUseSuffixPatternMatch(boolean) useSuffixPatternMatch}. The
	 * default value is {@code false}.
	 */
	public void setUseRegisteredSuffixPatternMatch(boolean useRegsiteredSuffixPatternMatch) {
		this.useRegisteredSuffixPatternMatch = useRegsiteredSuffixPatternMatch;
		this.useSuffixPatternMatch = useRegsiteredSuffixPatternMatch ? true : this.useSuffixPatternMatch;
	}
	
	/**
	 * Whether to use registered suffixes for pattern matching.
	 */
	public boolean useRegisteredSuffixPatternMatch() {
		return useRegisteredSuffixPatternMatch;
	}
	
	/**
	 * Whether to match to URLs irrespective of the presence of a trailing slash.
	 * If enabled a method mapped to "/users" also matches to "/users/".
	 * <p>The default value is {@code true}.
	 */
	public void setUseTrailingSlashMatch(boolean useTrailingSlashMatch) {
		this.useTrailingSlashMatch = useTrailingSlashMatch;
	}
	
	/**
	 * Whether to match to URLs irrespective of the presence of a trailing  slash.
	 */
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
	
	/**
	 * Provide a custom method-level request condition.
	 * The custom {@link RequestCondition} can be of any type so long as the
	 * same condition type is returned from all calls to this method in order
	 * to ensure custom request conditions can be combined and compared.
	 *
	 * <p>Consider extending {@link AbstractRequestCondition} for custom
	 * condition types and using {@link CompositeRequestCondition} to provide
	 * multiple custom conditions.
	 *
	 * @param method the handler method for which to create the condition
	 * @return the condition, or {@code null}
	 */
	protected RequestCondition<?> getCustomMethodCondition(Method method) {
		return null;
	}
	
	/**
	 * Resolve placeholder values in the given array of patterns.
	 * @return a new array with updated patterns
	 */
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
	
	/**
	 * Created a RequestMappingInfo from a RequestMapping annotation.
	 */
	protected RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, RequestCondition<?> customCondition) {

		String[] patterns = resolveEmbeddedValuesInPatterns(annotation.value());
		return new RequestMappingInfo(
				annotation.name(),
				new PatternsRequestCondition(patterns, getPathMatcher(),
						this.useSuffixPatternMatch, this.useTrailingSlashMatch, this.fileExtensions),
				new RequestMethodsRequestCondition(annotation.method())
				);
	}
	
	/**
	 * Provide a custom type-level request condition.
	 * The custom {@link RequestCondition} can be of any type so long as the
	 * same condition type is returned from all calls to this method in order
	 * to ensure custom request conditions can be combined and compared.
	 *
	 * <p>Consider extending {@link AbstractRequestCondition} for custom
	 * condition types and using {@link CompositeRequestCondition} to provide
	 * multiple custom conditions.
	 *
	 * @param handlerType the handler type for which to create the condition
	 * @return the condition, or {@code null}
	 */
	protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
		return null;
	}

	/**
	 * Uses method and type-level @{@link RequestMapping} annotations to create
	 * the RequestMappingInfo.
	 *
	 * @return the created RequestMappingInfo, or {@code null} if the method
	 * does not have a {@code @RequestMapping} annotation.
	 *
	 * @see #getCustomMethodCondition(Method)
	 * @see #getCustomTypeCondition(Class)
	 */
	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = null;
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
