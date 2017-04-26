package com.sharingif.cube.security.exception.validation.access;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;

/**
 *
 * @Description:  [Cube 访问决策异常]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年3月31日 下午1:05:32]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年3月31日 下午1:05:32]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class AccessDecisionCubeException extends ValidationCubeException{

	private static final long serialVersionUID = -3002342052259057722L;

	public AccessDecisionCubeException(String message){
		super(message);
	}
	public AccessDecisionCubeException(String message, String[] args){
		super(message,args);
	}
	public AccessDecisionCubeException(String message,Throwable cause){
		super(message,cause);
	}
	public AccessDecisionCubeException(String message, String[] args, Throwable cause){
		super(message,args,cause);
	}
	
}
