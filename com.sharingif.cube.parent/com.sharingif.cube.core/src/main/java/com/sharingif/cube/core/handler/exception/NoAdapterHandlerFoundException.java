package com.sharingif.cube.core.handler.exception;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**
 * NoAdapterHandlerFoundException
 * 2017/5/20 下午8:28
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NoAdapterHandlerFoundException extends CubeRuntimeException {

    public NoAdapterHandlerFoundException() {
        super("no adapterHandler found");
    }

}
