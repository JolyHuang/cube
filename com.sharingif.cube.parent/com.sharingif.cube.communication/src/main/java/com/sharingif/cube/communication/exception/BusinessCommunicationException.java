package com.sharingif.cube.communication.exception;

import com.sharingif.cube.core.exception.FieldError;
import com.sharingif.cube.core.exception.IFieldErrorCubeException;

import java.util.List;

/**
 * 业务通讯异常
 * 2016年4月12日 下午7:51:26
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class BusinessCommunicationException extends CommunicationException implements IFieldErrorCubeException {

	private static final long serialVersionUID = -2340347890799123297L;
	private List<FieldError> localeFieldErrors;
	
	public BusinessCommunicationException(String message){
		super(message);
	}

	public BusinessCommunicationException(String message, Object[] args){
		super(message,args);
	}

	@Override
	public List<FieldError> getLocaleFieldErrors() {
		return localeFieldErrors;
	}

	@Override
	public void setLocaleFieldErrors(List<FieldError> localeFieldErrors) {
		this.localeFieldErrors = localeFieldErrors;
	}
}
