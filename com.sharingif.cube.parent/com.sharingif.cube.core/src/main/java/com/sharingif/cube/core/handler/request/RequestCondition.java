package com.sharingif.cube.core.handler.request;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * The contract for request conditions.
 *
 * <p>Request conditions can be combined via {@link #combine(Object)}, matched to
 * a request via {@link #getMatchingCondition(HttpServletRequest)}, and compared
 * to each other via {@link #compareTo(Object, HttpServletRequest)} to determine
 * which matches a request more closely.
 *
 * @param <T> The type of objects that this RequestCondition can be combined
 * with compared to.
 *
 * @author Rossen Stoyanchev
 * @author Arjen Poutsma
 * @since 3.1
 */
public interface RequestCondition<T> {

	/**
	 * Defines the rules for combining this condition (i.e. the current instance)
	 * with another condition. For example combining type- and method-level
	 * {@link RequestMapping} conditions.
	 *
	 * @param other the condition to combine with.
	 * @return a request condition instance that is the result of combining
	 * 	the two condition instances.
	 */
	T combine(T other);

	/**
	 * Checks if this condition matches the given request and returns a
	 * potentially new request condition with content tailored to the
	 * current request. For example a condition with URL patterns might
	 * return a new condition that contains matching patterns sorted
	 * with best matching patterns on top.
	 *
	 * @return a condition instance in case of a match;
	 * 		or {@code null} if there is no match.
	 */
	T getMatchingCondition(RequestInfo<?> requestInfo);

	/**
	 * Compares this condition to another condition in the context of
	 * a specific request. This method assumes both instances have
	 * been obtained via {@link #getMatchingCondition(HttpServletRequest)}
	 * to ensure they have content relevant to current request only.
	 */
	int compareTo(T other, RequestInfo<?> requestInfo);

}
