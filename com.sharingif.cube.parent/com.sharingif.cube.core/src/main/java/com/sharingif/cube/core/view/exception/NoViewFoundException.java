package com.sharingif.cube.core.view.exception;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**
 * NoViewFoundException
 * 2017/5/20 下午9:02
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NoViewFoundException extends CubeRuntimeException {

	private static final long serialVersionUID = 8096404939862711228L;

	public NoViewFoundException() {
        super("no viewFound exception");
    }
}
