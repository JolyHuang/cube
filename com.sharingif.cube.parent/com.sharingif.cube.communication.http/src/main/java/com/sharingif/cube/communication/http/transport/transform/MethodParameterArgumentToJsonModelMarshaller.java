package com.sharingif.cube.communication.http.transport.transform;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.sharingif.cube.core.view.JsonModel;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.bind.support.DataBinderFactory;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.core.transport.transform.Marshaller;
import com.sharingif.cube.core.transport.transform.MethodParameterArgument;

/**
 * JsonUnmarshaller
 * 2017年1月7日 下午8:41:59
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MethodParameterArgumentToJsonModelMarshaller implements Marshaller<MethodParameterArgument<Object[], String>, JsonModel<Object>> {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String exceptionMessageName = JsonModel.EXCEPTION_MESSAGE;
	private String exceptionLocalizedMessageName = JsonModel.EXCEPTION_LOCALIZED_MESSAGE;
	private String tranStatusName= JsonModel.TRAN_STATUS;
	private String dataName = JsonModel.DATA;
	
	private String tranStatusSuccessValue;
	
	private ObjectMapper objectMapper;
	private BindingInitializer bindingInitializer;
	
	public MethodParameterArgumentToJsonModelMarshaller() {
		objectMapper = new ObjectMapper();
		objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
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
			this.logger.error("marshaller object to json error : {}", e);
			throw new MarshallerException("marshaller json to object error", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	protected JsonModel<Object> marshallerInternal(MethodParameterArgument<Object[], String> methodParameterArgument) throws MarshallerException, JsonParseException, JsonMappingException, IOException {
		Map<String, Object> connectReturnMap = objectMapper.readValue(methodParameterArgument.getConnectReturnValue(), Map.class);
		Object tranStatusObject = connectReturnMap.get(getTranStatusName());
		
		if(null == tranStatusObject) {
			this.logger.error("The transaction status field {} is not found in the message", getTranStatusName());
			throw new MarshallerException("tranStatus key name error");
		}
		
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
				data = convertAndAalidate(methodParameterArgument, dataObject);
			}
		} else {
			exceptionMessage = (String) connectReturnMap.get(getExceptionMessageName());
			exceptionLocalizedMessage = (String) connectReturnMap.get(getExceptionLocalizedMessageName());
		}
		
		return new JsonModel<Object>(tranStatus, exceptionMessage, exceptionLocalizedMessage, data);
	}
	
	protected Object convertAndAalidate(MethodParameterArgument<Object[], String> methodParameterArgument, Object data) {
		MethodParameter parameter = methodParameterArgument.getMethodParameter();
		RequestContext<Object[]> requestContext = methodParameterArgument.getRequestContext();
		DataBinderFactory dataBinderFactory = methodParameterArgument.getDataBinderFactory();
		
//		if(data instanceof List) {
//			List<Object> dataList = (List<Object>)data;
//			List<Object> returnDataList = new ArrayList(dataList.size());
//			for(Object obj : dataList) {
//				returnDataList.add(convertAndAalidate(parameter, requestContext, dataBinderFactory, (Map<?,?>)obj));
//			}
//			
//			return returnDataList;
//		}
		
		if(data instanceof List) {
			return data;
		}
	
		return convertAndAalidate(parameter, requestContext, dataBinderFactory, (Map<?,?>)data);
	}
	
	protected Object convertAndAalidate(MethodParameter parameter, RequestContext<Object[]> requestContext, DataBinderFactory dataBinderFactory, Map<?,?> dataMap) {
		DataBinder binder = null;
		try {
			String name = Conventions.getVariableNameForParameter(parameter);
			Object attribute = createAttribute(name, parameter, requestContext, dataBinderFactory);
			binder = dataBinderFactory.createBinder(requestContext, attribute, name);
		} catch (CubeException e) {
			throw new CubeRuntimeException("data binder error", e);
		}
		if (binder.getTarget() != null) {
			bindRequestParameters(binder, requestContext, dataMap);
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
	protected void bindRequestParameters(DataBinder binder, RequestContext<?> requestContext, Map<?,?> parameter) {
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
	protected Object createAttribute(String attributeName, MethodParameter methodParam, RequestContext<?> requestContext, DataBinderFactory dataBinderFactory) throws CubeException {

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
