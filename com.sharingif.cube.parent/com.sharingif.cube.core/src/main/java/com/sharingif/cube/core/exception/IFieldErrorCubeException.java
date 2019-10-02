package com.sharingif.cube.core.exception;

import java.util.List;

public interface IFieldErrorCubeException extends ICubeRuntimeException {

    List<FieldError> getLocaleFieldErrors();

    void setLocaleFieldErrors(List<FieldError> fieldErrors);

}
