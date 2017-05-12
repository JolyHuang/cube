package com.sharingif.cube.communication.http.transport.transform;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.transport.transform.Marshaller;
import com.sharingif.cube.communication.transport.transform.MethodParameterArgument;
import com.sharingif.cube.communication.transport.transform.exception.MarshallerException;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * JsonUnmarshaller
 * 2017年1月7日 下午8:41:59
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MethodParameterArgumentToJsonModelMarshaller implements Marshaller<MethodParameterArgument<Object[], String>, JsonModel<Object>> {
	
	private String exceptionMessageName = JsonModel.EXCEPTION_MESSAGE;
	private String exceptionLocalizedMessageName = JsonModel.EXCEPTION_LOCALIZED_MESSAGE;
	private String tranStatusName= JsonModel.TRAN_STATUS;
	private String dataName = JsonModel.DATA;
	
	private String tranStatusSuccessValue;
	
	private ObjectMapper objectMapper;
	private BindingInitializer bindingInitializer;
	
	public MethodParameterArgumentToJsonModelMarshaller() {
		objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+0800"));			// 中国上海
	}
	
	public String getExceptionMessageName() {
		return exceptionMessageName;
	}
	public void setExceptionMessageName(String exceptionMessageName) {
		this.exceptionMessageName = exceptionMessageName;
	}
	public String getExceptionLocalizedMessageName() {
		return exceptionLocalizedMessageName;
	}
	public void setExceptionLocalizedMessageName(String exceptionLocalizedMessageName) {
		this.exceptionLocalizedMessageName = exceptionLocalizedMessageName;
	}
	public String getTranStatusName() {
		return tranStatusName;
	}
	public void setTranStatusName(String tranStatusName) {
		this.tranStatusName = tranStatusName;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}


	public String getTranStatusSuccessValue() {
		return tranStatusSuccessValue;
	}
	public void setTranStatusSuccessValue(String tranStatusSuccessValue) {
		this.tranStatusSuccessValue = tranStatusSuccessValue;
	}

	public MethodParameterArgumentToJsonModelMarshaller(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	public BindingInitializer getBindingInitializer() {
		return bindingInitializer;
	}
	public void setBindingInitializer(BindingInitializer bindingInitializer) {
		this.bindingInitializer = bindingInitializer;
	}

	@Override
	public JsonModel<Object> marshaller(MethodParameterArgument<Object[], String> methodParameterArgument) throws MarshallerException {
		
		try {
			return marshallerInternal(methodParameterArgument);
		} catch (Exception e) {
			throw new MarshallerException("marshaller json to object error", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	protected JsonModel<Object> marshallerInternal(MethodParameterArgument<Object[], String> methodParameterArgument) throws MarshallerException, JsonParseException, JsonMappingException, IOException {
		Map<String, Object> connectReturnMap = objectMapper.readValue(methodParameterArgument.getConnectReturnValue(), Map.class);
		Object tranStatusObject = connectReturnMap.get(getTranStatusName());
		
		boolean tranStatus = false;
		Object data = null;
		String exceptionMessage = null;
		String exceptionLocalizedMessage = null;
		
		if(tranStatusObject instanceof Boolean) {
			tranStatus = (Boolean)tranStatusObject;
		} else {
			tranStatus = (((String)tranStatusObject).trim()).equals(tranStatusSuccessValue);
		}
		
		if(tranStatus) {
			Object dataObject = connectReturnMap.get(getDataName());
			MethodParameter parameter = methodParameterArgument.getMethodParameter();
			if((null != dataObject) && (!(parameter.getMethod().getReturnType().getName().equals(Void.TYPE.getName())))) {
				data = convertAndAalidate(methodParameterArgument, (Map<String, Object>) dataObject);
			}
		} else {
			exceptionMessage = (String) connectReturnMap.get(getExceptionMessageName());
			exceptionLocalizedMessage = (String) connectReturnMap.get(getExceptionLocalizedMessageName());
		}
		
		return new JsonModel<Object>(tranStatus, exceptionMessage, exceptionLocalizedMessage, data);
	}
	
	protected Object convertAndAalidate(MethodParameterArgument<Object[], String> methodParameterArgument, Map<String, Object> dataMap) {
		MethodParameter parameter = methodParameterArgument.getMethodParameter();
		RequestInfo<Object[]> requestInfo = methodParameterArgument.getRequestInfo();
		DataBinderFactory dataBinderFactory = methodParameterArgument.getDataBinderFactory();
		
		DataBinder binder = null;
		try {
			String name = Conventions.getVariableNameForParameter(methodParameterArgument.getMethodParameter());
			Object attribute = createAttribute(name, parameter, requestInfo, dataBinderFactory);
			binder = dataBinderFactory.createBinder(requestInfo, attribute, name);
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
