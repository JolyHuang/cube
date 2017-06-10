package com.sharingif.cube.core.exception.security;

import com.sharingif.cube.core.exception.CubeRuntimeException;

/**
 * 安全类异常，可对此类异常进行跟踪，生成安全监控系统
 * 2015年8月2日 下午11:03:33
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class SecurityCubeException extends CubeRuntimeException implements ISecurityCubeException {

	private static final long serialVersionUID = -3157347756340721166L;
	
	public SecurityCubeException(String message){
		super(message);
	}
	public SecurityCubeException(String message, Object[] args){
		super(message,args);
	}
	public SecurityCubeException(String message,Throwable cause){
		super(message,cause);
	}
	public SecurityCubeException(String message, Object[] args, Throwable cause){
		super(message,args,cause);
	}

}
