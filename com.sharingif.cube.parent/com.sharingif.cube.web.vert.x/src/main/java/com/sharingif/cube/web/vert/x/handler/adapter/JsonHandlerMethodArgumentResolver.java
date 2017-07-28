package com.sharingif.cube.web.vert.x.handler.adapter;

import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.vert.x.request.VertXRequestInfo;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;
import java.util.Map;

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
		MediaType candidateContentType = MediaType.parseMediaType(requestInfo.getMediaType());

		return MediaType.APPLICATION_JSON.isCompatibleWith(candidateContentType);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, RequestInfo<?> requestInfo, DataBinderFactory dataBinderFactory) throws CubeException {
		VertXRequestInfo vertXRequestInfo = (VertXRequestInfo)requestInfo;
		RoutingContext routingContext = vertXRequestInfo.getRoutingContext();
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
	
	protected void bindRequestParameters(DataBinder binder, RequestInfo<?> requestInfo, Map<?,?> parameter) {
		MutablePropertyValues mpvs = new MutablePropertyValues(parameter);
		binder.bind(mpvs);
	}
	
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
	
	protected Object createAttribute(String attributeName, MethodParameter methodParam, RequestInfo<?> requestInfo, DataBinderFactory dataBinderFactory) throws CubeException {

		return BeanUtils.instantiateClass(methodParam.getParameterType());
	}
	
	protected boolean isBindExceptionRequired(DataBinder binder, MethodParameter methodParam) {
		int i = methodParam.getParameterIndex();
		Class<?>[] paramTypes = methodParam.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
		return !hasBindingResult;
	}

}
