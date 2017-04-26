package com.sharingif.cube.communication.exception;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**
 * 通讯异常
 * 2016年4月12日 下午7:51:26
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class CommunicationException extends CubeRuntimeException {

	private static final long serialVersionUID = 3291265901781247507L;
	
	public CommunicationException(String message){
		super(message);
	}
	public CommunicationException(String message, Object[] args){
		super(message,args);
	}
	public CommunicationException(String message,Throwable cause){
		super(message,cause);
	}
	public CommunicationException(String message, Object[] args, Throwable cause){
		super(message,args,cause);
	}

}
