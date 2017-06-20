package com.sharingif.cube.web.springmvc.exception.handler.validation;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.web.exception.handler.validation.BindValidationCubeExceptionHandler;

/**
 * 数据验证
 * 2015年8月21日 下午9:52:28
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MethodArgumentNotValidExceptionHandler extends BindValidationCubeExceptionHandler {

	@Override
	public boolean supports(Exception exception) {
		return exception instanceof MethodArgumentNotValidException;
	}
	
	@Override
	protected ICubeException convertExceptionInternal(Exception exception) {
		MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException)exception;
		return new BindValidationCubeException(methodArgumentNotValidException.getBindingResult());
	}
	
}
