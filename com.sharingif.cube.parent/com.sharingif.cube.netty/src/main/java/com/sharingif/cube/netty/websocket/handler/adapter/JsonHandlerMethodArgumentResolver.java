package com.sharingif.cube.netty.websocket.handler.adapter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.netty.websocket.request.WebSocketRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.TimeZone;

/**
 * 处理json参数
 * 2016年12月19日 下午7:17:54
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class JsonHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ObjectMapper objectMapper;

	public JsonHandlerMethodArgumentResolver() {
		objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
	}

	public JsonHandlerMethodArgumentResolver(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter, RequestContext<?> requestContext) {
		MediaType candidateContentType = MediaType.parseMediaType(requestContext.getMediaType());

		return MediaType.APPLICATION_JSON.isCompatibleWith(candidateContentType);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, RequestContext<?> requestContext, DataBinderFactory dataBinderFactory) throws CubeException {
		WebSocketRequestContext vertXRequestContext = (WebSocketRequestContext)requestContext;
		String data = vertXRequestContext.getRequest().get_data();
		if(data == null) {
			return null;
		}

		String name = Conventions.getVariableNameForParameter(parameter);
		Object arg = readWithMessageConverters(parameter, data);

		DataBinder binder = dataBinderFactory.createBinder(requestContext, arg, name);
		if (arg != null) {
			validateIfApplicable(binder, parameter);
			if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
				throw new BindValidationCubeException(binder.getBindingResult());
			}
		}

		return binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
	}

	public Object readWithMessageConverters(MethodParameter parameter, String data) throws MarshallerException {
		JavaType javaType = getJavaType(parameter.getNestedGenericParameterType());

		try {
			return objectMapper.readValue(data, javaType);
		} catch (Exception e) {
			this.logger.error("marshaller object to json error : {}", e);
			throw new MarshallerException("marshaller json to object error", e);
		}

	}

	protected JavaType getJavaType(Type type) {
		TypeFactory typeFactory = this.objectMapper.getTypeFactory();
		return typeFactory.constructType(type);
	}

	protected void validateIfApplicable(DataBinder binder, MethodParameter parameter) {
		Annotation[] annotations = parameter.getParameterAnnotations();
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

	protected boolean isBindExceptionRequired(DataBinder binder, MethodParameter parameter) {
		int i = parameter.getParameterIndex();
		Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
		return !hasBindingResult;
	}

}