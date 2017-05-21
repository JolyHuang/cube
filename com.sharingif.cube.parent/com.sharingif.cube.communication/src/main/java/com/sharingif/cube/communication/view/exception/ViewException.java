package com.sharingif.cube.communication.view.exception;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**
 * 视图处理异常
 * 2017/5/20 下午9:14
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ViewException extends CubeRuntimeException {

	private static final long serialVersionUID = -3986579921533402039L;

	public ViewException(Throwable cause) {
        super("handler view error", cause);
    }
}
