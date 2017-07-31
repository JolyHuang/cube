package com.sharingif.cube.web.exception.handler.validation;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestInfo;
import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.core.handler.HandlerMethod;
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
	
	@Override
	public boolean supports(Exception exception) {
		if(exception instanceof BindException) {
			return true;
		}
		return exception instanceof BindValidationCubeException;
	}
	
	@Override
	protected ICubeException convertExceptionInternal(Exception exception) {
		if(exception instanceof BindException) {
			return new BindValidationCubeException((BindException)exception);
		}

		return (BindValidationCubeException)exception;
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
	public void wirteLogInternal(HttpRequestInfo<HttpRequest, HttpResponse> requestInfo, HandlerMethod handlerMethod, ICubeException cubeException, Locale locale, Long exTime) {
		
		String loggerMessage = "transaction error===> ThdId:{}, method:{}, TrsId:{}, ExTime:{} \nmessage:{} \nlocalizedMessage:{}";
		
		if(null == ((Exception)cubeException).getCause()){
			
			this.logger.error(loggerMessage
					,Thread.currentThread().getId()
					,requestInfo.getMethod()
					,requestInfo.getLookupPath()
					,exTime
					,cubeException.getMessage()
					,((BindValidationCubeException)cubeException).getFieldErrors()
					);
			return ;
		}
		
		this.logger.error(loggerMessage
				,Thread.currentThread().getId()
				,requestInfo.getMethod()
				,requestInfo.getLookupPath()
				,exTime
				,cubeException.getMessage()
				,cubeException.getLocalizedMessage()
				,cubeException
				);
	}

	@Override
	public ExceptionContent handlerException(HttpRequestInfo<HttpRequest, HttpResponse> requestInfo,
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
