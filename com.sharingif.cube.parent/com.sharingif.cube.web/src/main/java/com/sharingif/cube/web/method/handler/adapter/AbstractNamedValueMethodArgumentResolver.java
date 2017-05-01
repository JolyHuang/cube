package com.sharingif.cube.web.method.handler.adapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * Abstract base class for resolving method arguments from a named value.
 * Request parameters, request headers, and path variables are examples of named
 * values. Each may have a name, a required flag, and a default value.
 * <p>Subclasses define how to do the following:
 * <ul>
 * <li>Obtain named value information for a method parameter
 * <li>Resolve names into argument values
 * <li>Handle missing argument values when argument values are required
 * <li>Optionally handle a resolved value
 * </ul>
 * <p>A default value string can contain ${...} placeholders and Spring Expression
 * Language #{...} expressions. For this to work a
 * {@link ConfigurableBeanFactory} must be supplied to the class constructor.
 * <p>A {@link WebDataBinder} is created to apply type conversion to the resolved
 * argument value if it doesn't match the method parameter type.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 3.1
 */
public abstract class AbstractNamedValueMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	private final ConfigurableBeanFactory configurableBeanFactory;

	private final BeanExpressionContext expressionContext;
	
	private Map<MethodParameter, NamedValueInfo> namedValueInfoCache = new ConcurrentHashMap<MethodParameter, NamedValueInfo>(256);
	
	
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
		this.expressionContext = (beanFactory != null ? new BeanExpressionContext(beanFactory, new RequestScope()) : null);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, RequestInfo<?> requestInfo, DataBinderFactory dataBinderFactory) throws CubeException {
		
		Class<?> paramType = parameter.getParameterType();
		NamedValueInfo namedValueInfo = getNamedValueInfo(parameter);
		
		Object arg = resolveName(namedValueInfo.name, parameter, requestInfo);
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
			DataBinder binder = dataBinderFactory.createBinder(requestInfo, null, namedValueInfo.name);
			arg = binder.convertIfNecessary(arg, paramType, parameter);
		}

		handleResolvedValue(arg, namedValueInfo.name, parameter, requestInfo);

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
		String defaultValue = (ValueConstants.DEFAULT_NONE.equals(info.defaultValue) ? null : info.defaultValue);
		return new NamedValueInfo(name, info.required, defaultValue);
	}
	
	/**
	 * Resolves the given parameter type and value name into an argument value.
	 * @param name the name of the value being resolved
	 * @param parameter the method parameter to resolve to an argument value
	 * @param request the current request
	 * @return the resolved argument. May be {@code null}
	 * @throws Exception in case of errors
	 */
	protected abstract Object resolveName(String name, MethodParameter parameter, RequestInfo<?> requestInfo)
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

	/**
	 * Invoked when a named value is required, but {@link #resolveName(String, MethodParameter, NativeWebRequest)}
	 * returned {@code null} and there is no default value. Subclasses typically throw an exception in this case.
	 * @param name the name for the value
	 * @param parameter the method parameter
	 */
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

	/**
	 * Invoked after a value is resolved.
	 * @param arg the resolved argument value
	 * @param name the argument name
	 * @param parameter the argument parameter type
	 * @param mavContainer the {@link ModelAndViewContainer}, which may be {@code null}
	 * @param webRequest the current request
	 */
	protected void handleResolvedValue(Object arg, String name, MethodParameter parameter,
			RequestInfo<?> requestInfo) {
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
