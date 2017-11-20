package com.sharingif.cube.core.handler.request;



import com.sharingif.cube.core.handler.RequestMappingInfoHandlerMethodMappingNamingStrategy;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;

/**
 * Encapsulates the following request mapping conditions:
 * <ol>
 * 	<li>{@link PatternsRequestCondition}
 * 	<li>{@code RequestCondition<?>} (optional, custom request condition)
 * </ol>
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 3.1
 */
public final class RequestMappingInfo implements RequestCondition<RequestMappingInfo> {
	
	private final String name;

	private final PatternsRequestCondition patternsCondition;
	
	private final RequestMethodsRequestCondition methodsCondition;

	private int hash;

	/**
	 * Creates a new instance with the given request conditions.
	 */
	public RequestMappingInfo(String name, PatternsRequestCondition patterns, RequestMethodsRequestCondition methods) {
		this.name = (StringUtils.hasText(name) ? name : null);
		this.patternsCondition = patterns != null ? patterns : new PatternsRequestCondition();
		this.methodsCondition = (methods != null ? methods : new RequestMethodsRequestCondition());
	}
	
	/**
	 * Return the name for this mapping, or {@code null}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the URL patterns of this {@link RequestMappingInfo};
	 * or instance with 0 patterns, never {@code null}.
	 */
	public PatternsRequestCondition getPatternsCondition() {
		return this.patternsCondition;
	}

	/**
	 * Returns the HTTP request methods of this {@link RequestMappingInfo};
	 * or instance with 0 request methods, never {@code null}.
	 */
	public RequestMethodsRequestCondition getMethodsCondition() {
		return this.methodsCondition;
	}


	/**
	 * Combines "this" request mapping info (i.e. the current instance) with another request mapping info instance.
	 * <p>Example: combine type- and method-level request mappings.
	 * @return a new request mapping info instance; never {@code null}
	 */
	public RequestMappingInfo combine(RequestMappingInfo other) {
		String name = combineNames(other);
		PatternsRequestCondition patterns = this.patternsCondition.combine(other.patternsCondition);
		RequestMethodsRequestCondition methods = this.methodsCondition.combine(other.methodsCondition);

		return new RequestMappingInfo(name, patterns, methods);
	}
	
	private String combineNames(RequestMappingInfo other) {
		if (this.name != null && other.name != null) {
			String separator = RequestMappingInfoHandlerMethodMappingNamingStrategy.SEPARATOR;
			return this.name + separator + other.name;
		}
		else if (this.name != null) {
			return this.name;
		}
		else {
			return (other.name != null ? other.name : null);
		}
	}

	/**
	 * Checks if all conditions in this request mapping info match the provided request and returns
	 * a potentially new request mapping info with conditions tailored to the current request.
	 * <p>For example the returned instance may contain the subset of URL patterns that match to
	 * the current request, sorted with best matching patterns on top.
	 * @return a new instance in case all conditions match; or {@code null} otherwise
	 */
	public RequestMappingInfo getMatchingCondition(RequestContext<?> requestContext) {
		
		RequestMethodsRequestCondition methods = this.methodsCondition.getMatchingCondition(requestContext);
		
		if (methods == null) {
			return null;
		}
		
		PatternsRequestCondition patterns = patternsCondition.getMatchingCondition(requestContext);
		if (patterns == null) {
			return null;
		}

		return new RequestMappingInfo(this.name, patterns, methods);
	}

	/**
	 * Compares "this" info (i.e. the current instance) with another info in the context of a request.
	 * <p>Note: it is assumed both instances have been obtained via
	 * {@link #getMatchingCondition(HttpServletRequest)} to ensure they have conditions with
	 * content relevant to current request.
	 */
	public int compareTo(RequestMappingInfo other, RequestContext<?> requestContext) {
		int result = patternsCondition.compareTo(other.getPatternsCondition(), requestContext);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj != null && obj instanceof RequestMappingInfo) {
			RequestMappingInfo other = (RequestMappingInfo) obj;
			return (this.patternsCondition.equals(other.patternsCondition));
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = hash;
		if (result == 0) {
			result = patternsCondition.hashCode();
			hash = result;
		}
		return result;
	}

}
