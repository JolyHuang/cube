package com.sharingif.cube.web.handler.adapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.validation.DataBinder;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;

public abstract class AbstractNamedValueMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private final ConfigurableBeanFactory configurableBeanFactory;

	private final BeanExpressionContext expressionContext;

	private Map<MethodParameter, NamedValueInfo> namedValueInfoCache = new ConcurrentHashMap<MethodParameter, NamedValueInfo>(256);

	protected static final String DEFAULT_NONE = "\n\t\t\n\t\t\n\uE000\uE001\uE002\n\t\t\t\t\n";

	public AbstractNamedValueMethodArgumentResolver() {
		this.configurableBeanFactory = null;
		this.expressionContext = null;
	}

	/**
	 * @param beanFactory a bean factory to use for resolving ${...} placeholder
	 * and #{...} SpEL expressions in default values, or {@code null} if default
	 * values are not expected to contain expressions
	 */
	public AbstractNamedValueMethodArgumentResolver(ConfigurableBeanFactory beanFactory) {
		this.configurableBeanFactory = beanFactory;
		this.expressionContext = (beanFactory != null ? new BeanExpressionContext(beanFactory, null) : null);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, RequestContext<?> requestContext, DataBinderFactory dataBinderFactory) throws CubeException {

		Class<?> paramType = parameter.getParameterType();
		NamedValueInfo namedValueInfo = getNamedValueInfo(parameter);

		Object arg = resolveName(namedValueInfo.name, parameter, requestContext);
		if (arg == null) {
			if (namedValueInfo.defaultValue != null) {
				arg = resolveDefaultValue(namedValueInfo.defaultValue);
			}
			else if (namedValueInfo.required && !parameter.getParameterType().getName().equals("java.util.Optional")) {
				handleMissingValue(namedValueInfo.name, parameter);
			}
			arg = handleNullValue(namedValueInfo.name, arg, paramType);
		}
		else if ("".equals(arg) && namedValueInfo.defaultValue != null) {
			arg = resolveDefaultValue(namedValueInfo.defaultValue);
		}

		if (dataBinderFactory != null) {
			DataBinder binder = dataBinderFactory.createBinder(requestContext, null, namedValueInfo.name);
			arg = binder.convertIfNecessary(arg, paramType, parameter);
		}

		handleResolvedValue(arg, namedValueInfo.name, parameter, requestContext);

		return arg;
	}

	/**
	 * Obtain the named value for the given method parameter.
	 */
	private NamedValueInfo getNamedValueInfo(MethodParameter parameter) {
		NamedValueInfo namedValueInfo = this.namedValueInfoCache.get(parameter);
		if (namedValueInfo == null) {
			namedValueInfo = createNamedValueInfo(parameter);
			namedValueInfo = updateNamedValueInfo(parameter, namedValueInfo);
			this.namedValueInfoCache.put(parameter, namedValueInfo);
		}
		return namedValueInfo;
	}

	/**
	 * Create the {@link NamedValueInfo} object for the given method parameter. Implementations typically
	 * retrieve the method annotation by means of {@link MethodParameter#getParameterAnnotation(Class)}.
	 * @param parameter the method parameter
	 * @return the named value information
	 */
	protected abstract NamedValueInfo createNamedValueInfo(MethodParameter parameter);

	/**
	 * Create a new NamedValueInfo based on the given NamedValueInfo with sanitized values.
	 */
	private NamedValueInfo updateNamedValueInfo(MethodParameter parameter, NamedValueInfo info) {
		String name = info.name;
		if (info.name.length() == 0) {
			name = parameter.getParameterName();
			if (name == null) {
				throw new IllegalArgumentException("Name for argument type [" + parameter.getParameterType().getName() +
						"] not available, and parameter name information not found in class file either.");
			}
		}
		String defaultValue = (DEFAULT_NONE.equals(info.defaultValue) ? null : info.defaultValue);
		return new NamedValueInfo(name, info.required, defaultValue);
	}

	protected abstract Object resolveName(String name, MethodParameter parameter, RequestContext<?> requestContext)
			throws CubeException;

	/**
	 * Resolves the given default value into an argument value.
	 */
	private Object resolveDefaultValue(String defaultValue) {
		if (this.configurableBeanFactory == null) {
			return defaultValue;
		}
		String placeholdersResolved = this.configurableBeanFactory.resolveEmbeddedValue(defaultValue);
		BeanExpressionResolver exprResolver = this.configurableBeanFactory.getBeanExpressionResolver();
		if (exprResolver == null) {
			return defaultValue;
		}
		return exprResolver.evaluate(placeholdersResolved, this.expressionContext);
	}

	protected abstract void handleMissingValue(String name, MethodParameter parameter) throws CubeException;

	/**
	 * A {@code null} results in a {@code false} value for {@code boolean}s or an exception for other primitives.
	 */
	private Object handleNullValue(String name, Object value, Class<?> paramType) {
		if (value == null) {
			if (Boolean.TYPE.equals(paramType)) {
				return Boolean.FALSE;
			}
			else if (paramType.isPrimitive()) {
				throw new IllegalStateException("Optional " + paramType + " parameter '" + name +
						"' is present but cannot be translated into a null value due to being declared as a " +
						"primitive type. Consider declaring it as object wrapper for the corresponding primitive type.");
			}
		}
		return value;
	}

	protected void handleResolvedValue(Object arg, String name, MethodParameter parameter,
			RequestContext<?> requestContext) {
	}

	/**
	 * Represents the information about a named value, including name, whether it's required and a default value.
	 */
	protected static class NamedValueInfo {

		private final String name;

		private final boolean required;

		private final String defaultValue;

		public NamedValueInfo(String name, boolean required, String defaultValue) {
			this.name = name;
			this.required = required;
			this.defaultValue = defaultValue;
		}
	}

}
