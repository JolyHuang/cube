package com.sharingif.cube.core.exception.security;

/**
 * 非法操作异常
 * 2015年10月3日 下午9:09:28
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class LllegalOperationSecurityCubeException extends SecurityCubeException {

	private static final long serialVersionUID = 610106483543490585L;
	
	private static final String ERROR_MESSAGE="lllegal operation";
	
	public LllegalOperationSecurityCubeException(){
		super(ERROR_MESSAGE);
	}

}
