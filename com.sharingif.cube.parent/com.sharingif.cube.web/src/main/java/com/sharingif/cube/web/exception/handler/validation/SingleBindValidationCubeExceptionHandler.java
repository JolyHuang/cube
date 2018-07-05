package com.sharingif.cube.web.exception.handler.validation;

import java.util.List;
import java.util.Locale;

import org.springframework.validation.FieldError;

import com.sharingif.cube.core.exception.ICubeException;
import com.sharingif.cube.core.exception.validation.BindValidationCubeException;

/**
 *
 * 只返回一个错误信息
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/8/14 下午2:30
 */
public class SingleBindValidationCubeExceptionHandler extends BindValidationCubeExceptionHandler {

    @Override
    public void resolverMessages(ICubeException cubeException, Locale locale) {
        super.resolverMessages(cubeException, locale);

        BindValidationCubeException bindValidationCubeException = (BindValidationCubeException)cubeException;
        List<FieldError> localeFieldErrors = bindValidationCubeException.getLocaleFieldErrors();

        cubeException.setLocalizedMessage(localeFieldErrors.get(0).getDefaultMessage());
    }

}
