package com.sharingif.cube.core.transport.exception;

import com.sharingif.cube.core.exception.CubeException;

/**
 * 对象转换为其它格式数据异常
 * 2016年3月30日 下午7:29:17
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class MarshallerException extends CubeException {

	private static final long serialVersionUID = -2449730991754475037L;
	
	public MarshallerException(String message) {
		super(message);
	}
	
	public MarshallerException(String message, Throwable cause) {
		super(message, cause);
	}

}
