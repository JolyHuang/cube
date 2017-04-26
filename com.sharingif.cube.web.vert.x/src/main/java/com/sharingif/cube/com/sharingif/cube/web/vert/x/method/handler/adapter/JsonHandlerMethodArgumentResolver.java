package com.sharingif.cube.com.sharingif.cube.web.vert.x.method.handler.adapter;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.core.method.bind.support.DataBinderFactory;
import com.sharingif.cube.core.method.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.request.RequestInfo;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;

/**
 * 处理json参数
 * 2016年12月19日 下午7:17:54
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class JsonHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter, RequestInfo<?> requestInfo) {
		return MediaType.APPLICATION_JSON.toString().equals(requestInfo.getMediaType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, RequestInfo<?> requestInfo, DataBinderFactory dataBinderFactory) throws CubeException {
		RoutingContext routingContext = (RoutingContext) requestInfo.getRequest();
		Buffer buffer = routingContext.getBody();
		if(buffer.length() == 0) {
			return null;
		}
		
		String name = Conventions.getVariableNameForParameter(parameter);
		Object attribute = createAttribute(name, parameter, requestInfo, dataBinderFactory);
		DataBinder binder = dataBinderFactory.createBinder(requestInfo, attribute, name);
		if (binder.getTarget() != null) {
			bindRequestParameters(binder, requestInfo, routingContext.getBodyAsJson().getMap());
			validateIfApplicable(binder, parameter);
			if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
				throw new BindValidationCubeException(binder.getBindingResult());
			}
		}
		
		return binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
	}
	
	/**
	 * Extension point to bind the request to the target object.
	 * @param binder the data binder instance to use for the binding
	 * @param request the current request
	 */
	protected void bindRequestParameters(DataBinder binder, RequestInfo<?> requestInfo, Map<?,?> parameter) {
		MutablePropertyValues mpvs = new MutablePropertyValues(parameter);
		binder.bind(mpvs);
	}
	
	/**
	 * Validate the model attribute if applicable.
	 * <p>The default implementation checks for {@code @javax.validation.Valid},
	 * Spring's {@link org.springframework.validation.annotation.Validated},
	 * and custom annotations whose name starts with "Valid".
	 * @param binder the DataBinder to be used
	 * @param methodParam the method parameter
	 */
	protected void validateIfApplicable(DataBinder binder, MethodParameter methodParam) {
		Annotation[] annotations = methodParam.getParameterAnnotations();
		for (Annotation ann : annotations) {
			Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
			if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
				Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
				Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[] {hints});
				binder.validate(validationHints);
				break;
			}
		}
	}
	
	/**
	 * Extension point to create the model attribute if not found in the model.
	 * The default implementation uses the default constructor.
	 * @param attributeName the name of the attribute (never {@code null})
	 * @param methodParam the method parameter
	 * @param binderFactory for creating WebDataBinder instance
	 * @param request the current request
	 * @return the created model attribute (never {@code null})
	 */
	protected Object createAttribute(String attributeName, MethodParameter methodParam, RequestInfo<?> requestInfo, DataBinderFactory dataBinderFactory) throws CubeException {

		return BeanUtils.instantiateClass(methodParam.getParameterType());
	}
	
	/**
	 * Whether to raise a fatal bind exception on validation errors.
	 * @param binder the data binder used to perform data binding
	 * @param methodParam the method argument
	 * @return {@code true} if the next method argument is not of type {@link Errors}
	 */
	protected boolean isBindExceptionRequired(DataBinder binder, MethodParameter methodParam) {
		int i = methodParam.getParameterIndex();
		Class<?>[] paramTypes = methodParam.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
		return !hasBindingResult;
	}

}
