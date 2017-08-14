package com.sharingif.cube.web.springmvc.exception.handler.validation;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;
import com.sharingif.cube.web.exception.handler.validation.SingleBindValidationCubeExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * 只返回一个错误信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/8/14 下午3:58
 */
public class SingleMethodArgumentNotValidExceptionHandler extends SingleBindValidationCubeExceptionHandler {

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
