package com.sharingif.cube.communication.http.transport;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.exception.BusinessCommunicationException;
import com.sharingif.cube.communication.transport.AbstractRemoteHandlerMethodTransport;
import com.sharingif.cube.communication.transport.Connection;
import com.sharingif.cube.communication.transport.transform.Transform;
import com.sharingif.cube.communication.transport.transform.exception.MarshallerException;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.core.method.HandlerMethod;
import com.sharingif.cube.core.method.HandlerMethodContent;
import com.sharingif.cube.core.method.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.core.util.CubeExceptionUtil;

/**
 * 提供远程方法访问
 * 2017年1月9日 下午4:09:16
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class HttpJsonRemoteHandlerMethodTransport extends AbstractRemoteHandlerMethodTransport<RequestInfo<Object[]>,Object[],String,String,JsonModel<Map<String, Object>>,RequestInfo<String>,String> {
	
	private Connection<RequestInfo<String>,String> connection;
	private Transform<Object[],String, String, JsonModel<Map<String, Object>>> transform;

	public HttpJsonRemoteHandlerMethodTransport(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}

	public void setConnection(Connection<RequestInfo<String>,String> connection) {
		this.connection = connection;
	}
	
	public void setTransform(Transform<Object[], String, String, JsonModel<Map<String, Object>>> transform) {
		this.transform = transform;
	}

	@Override
	public Object doTransport(RequestInfo<Object[]> requestInfo) {
		
		Object returnValue = null;
		boolean handlerMethodChainIsEmpty = (null != getHandlerMethodChain());
		HandlerMethodContent handlerMethodContent = null;
		try {
			if(handlerMethodChainIsEmpty) {
				handlerMethodContent = new HandlerMethodContent(getBean(), getMethod(), requestInfo.getRequest(), null, getMethodParameters(), requestInfo.getLocale(), requestInfo);
				getHandlerMethodChain().before(handlerMethodContent);
			}
			
			RequestInfo<String> httpJsonContext = null;
			try {
				String jsonData = transform.marshaller(requestInfo.getRequest());
				
				httpJsonContext = new RequestInfo<String>(requestInfo.getMediaType(), requestInfo.getLookupPath(), requestInfo.getLocale(), requestInfo.getMethod(), jsonData);
			} catch (MarshallerException e) {
				throw new CubeRuntimeException(e.getMessage(),e);
			}
			
			String resultData = connection.connect(httpJsonContext);
			
			JsonModel<Map<String, Object>> jsonModel = null;
			try {
				jsonModel = transform.unmarshaller(resultData);
			} catch (MarshallerException e) {
				throw new CubeRuntimeException(e.getMessage(),e);
			}
			
			exceptionHandler(jsonModel);
			
			Map<String,Object> dataMap = jsonModel.get_data();
			
			MethodParameter parameter = getReturnType();
			
			if(dataMap == null || parameter.getMethod().getReturnType().getName().equals(Void.TYPE.getName()) ) {
				return null;
			}
			
			DataBinder binder = null;
			try {
				String name = Conventions.getVariableNameForParameter(parameter);
				Object attribute = createAttribute(name, parameter, requestInfo, getDataBinderFactory());
				binder = getDataBinderFactory().createBinder(requestInfo, attribute, name);
			} catch (CubeException e) {
				throw new CubeRuntimeException("data binder error", e);
			}
			if (binder.getTarget() != null) {
				bindRequestParameters(binder, requestInfo, dataMap);
				validateIfApplicable(binder, parameter);
				if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
					throw new BindValidationCubeException(binder.getBindingResult());
				}
			}
			
			returnValue = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
			if(handlerMethodChainIsEmpty) {
				handlerMethodContent.setReturnValue(returnValue);
				getHandlerMethodChain().after(handlerMethodContent);
			}
		} catch (Exception e) {
			if(handlerMethodChainIsEmpty) {
				getHandlerMethodChain().exception(handlerMethodContent, e);
			}
			CubeExceptionUtil.throwCubeRuntimeException(e);
		}
		
		if(handlerMethodChainIsEmpty) {
			return handlerMethodContent.getReturnValue();
		} else {
			return returnValue;
		}
	}
	
	protected void exceptionHandler(JsonModel<Map<String, Object>> jsonModel) throws BusinessCommunicationException {
		if(!jsonModel.get_tranStatus()) {
			BusinessCommunicationException businessCommunicationException = new BusinessCommunicationException(jsonModel.get_exceptionMessage());
			businessCommunicationException.setLocalizedMessage(jsonModel.get_exceptionLocalizedMessage());
			
			throw businessCommunicationException;
		}
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
