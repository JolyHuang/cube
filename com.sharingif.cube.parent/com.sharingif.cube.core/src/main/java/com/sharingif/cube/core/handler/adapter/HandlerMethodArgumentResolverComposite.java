package com.sharingif.cube.core.handler.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;


/**
 * Resolves method parameters by delegating to a list of registered {@link HandlerMethodArgumentResolver}s.
 * Previously resolved method parameters are cached for faster lookups.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 */
public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final List<HandlerMethodArgumentResolver> argumentResolvers =
			new LinkedList<HandlerMethodArgumentResolver>();

	private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache =
			new ConcurrentHashMap<MethodParameter, HandlerMethodArgumentResolver>(256);
	
	/**
	 * Return a read-only list with the contained resolvers, or an empty list.
	 */
	public List<HandlerMethodArgumentResolver> getResolvers() {
		return Collections.unmodifiableList(this.argumentResolvers);
	}

	/**
	 * Whether the given {@linkplain MethodParameter method parameter} is supported by any registered
	 * {@link HandlerMethodArgumentResolver}.
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter, RequestContext<?> requestContext) {
		return getArgumentResolver(parameter, requestContext) != null;
	}

	/**
	 * Iterate over registered {@link HandlerMethodArgumentResolver}s and invoke the one that supports it.
	 * @throws IllegalStateException if no suitable {@link HandlerMethodArgumentResolver} is found.
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, RequestContext<?> requestContext, DataBinderFactory dataBinderFactory) throws CubeException {

		HandlerMethodArgumentResolver resolver = getArgumentResolver(parameter, requestContext);
		Assert.notNull(resolver, "Unknown parameter type [" + parameter.getParameterType().getName() + "]");
		return resolver.resolveArgument(parameter, requestContext, dataBinderFactory);
	}

	/**
	 * Find a registered {@link HandlerMethodArgumentResolver} that supports the given method parameter.
	 */
	private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter, RequestContext<?> requestContext) {
		HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
		if (result == null) {
			for (HandlerMethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
				if (logger.isTraceEnabled()) {
					logger.trace("Testing if argument resolver [" + methodArgumentResolver + "] supports [" +
							parameter.getGenericParameterType() + "]");
				}
				if (methodArgumentResolver.supportsParameter(parameter, requestContext)) {
					result = methodArgumentResolver;
					this.argumentResolverCache.put(parameter, result);
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Add the given {@link HandlerMethodArgumentResolver}.
	 */
	public HandlerMethodArgumentResolverComposite addResolver(HandlerMethodArgumentResolver resolver) {
		this.argumentResolvers.add(resolver);
		return this;
	}

	/**
	 * Add the given {@link HandlerMethodArgumentResolver}s.
	 */
	public HandlerMethodArgumentResolverComposite addResolvers(List<? extends HandlerMethodArgumentResolver> resolvers) {
		if (resolvers != null) {
			for (HandlerMethodArgumentResolver resolver : resolvers) {
				this.argumentResolvers.add(resolver);
			}
		}
		return this;
	}

}
