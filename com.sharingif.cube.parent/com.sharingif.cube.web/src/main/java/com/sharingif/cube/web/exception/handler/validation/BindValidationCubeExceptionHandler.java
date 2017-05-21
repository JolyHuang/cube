package com.sharingif.cube.web.exception.handler.validation;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.exception.handler.WebRequestInfo;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

/**
 * 数据验证
 * 2015年8月17日 下午10:37:15
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class BindValidationCubeExceptionHandler extends ValidationCubeExceptionHandler {
	
	private String fieldErrors="_fieldErrors";
	
	public String getFieldErrors() {
		return fieldErrors;
	}
	public void setFieldErrors(String fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	@Override
	public boolean supports(Exception exception) {
		return exception instanceof BindException;
	}
	
	@Override
	public ICubeException convertException(Exception exception) {
		return new BindValidationCubeException((BindException)exception);
	}
	
	

	@Override
	public void resolverMessages(ICubeException cubeException, Locale locale) {
		super.resolverMessages(cubeException, locale);
		
		BindValidationCubeException bindValidationCubeException = (BindValidationCubeException)cubeException;
		List<FieldError> fieldErrors = bindValidationCubeException.getBindingResult().getFieldErrors();
		List<FieldError> localeFieldErrors = new ArrayList<FieldError>(fieldErrors.size());
		for (FieldError fieldError : fieldErrors) {
			
			String localizedMessage=super.getApplicationContext().getMessage(fieldError, locale);
			FieldError localeFieldError = new FieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.isBindingFailure(), fieldError.getCodes(), fieldError.getArguments(), localizedMessage);
			localeFieldErrors.add(localeFieldError);
		}
		bindValidationCubeException.setLocaleFieldErrors(localeFieldErrors);
	}

	@Override
	public void wirteLogInternal(RequestInfo<WebRequestInfo> requestInfo, HandlerMethod handlerMethod, ICubeException cubeException, Locale locale, Long exTime) {
		
		String loggerMessage = "transaction error===> ThdId:{}, TrsId:{}.{}, ExTime:{} \nmessage:{} \nlocalizedMessage:{}";
		
		if(null == ((Exception)cubeException).getCause()){
			
			this.logger.error(loggerMessage
					,Thread.currentThread().getId()
					,handlerMethod.getBean().getClass().getName()
					,handlerMethod.getMethod().getName()
					,exTime
					,cubeException.getMessage()
					,((BindValidationCubeException)cubeException).getFieldErrors()
					);
			return ;
		}
		
		this.logger.error(loggerMessage
				,Thread.currentThread().getId()
				,handlerMethod.getBean().getClass().getName()
				,handlerMethod.getMethod().getName()
				,exTime
				,cubeException.getMessage()
				,cubeException.getLocalizedMessage()
				,cubeException
				);
	}

	@Override
	public ExceptionContent handlerException(RequestInfo<WebRequestInfo> requestInfo,
			HandlerMethod handlerMethod,
			ICubeException cubeException) {

		ExceptionContent out = super.handlerException(requestInfo, handlerMethod, cubeException);
		Map<String,Object> modelMap = out.getModel();
		if(modelMap == null){
			modelMap = new HashMap<String,Object>(1);
			out.setModel(modelMap);
		}
		
		BindValidationCubeException bindValidationCubeException = (BindValidationCubeException)cubeException;
		
		Map<String, Object> model = bindValidationCubeException.getBindingResult().getModel();
		
		modelMap.put(fieldErrors, bindValidationCubeException.getLocaleFieldErrors());
		
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof BindingResult){
				modelMap.put(key, value);
				return out;
			}
		}
		
		
		return out;
	}
	

}
