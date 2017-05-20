package com.sharingif.cube.core.handler.exception;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**
 * NoHandlerMappingFoundException
 * 2017/5/20 下午8:24
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NoHandlerMappingFoundException extends CubeRuntimeException {

    public NoHandlerMappingFoundException() {
        super("no handlerMapping found");
    }

}
