package com.sharingif.cube.security.exception.validation.authentication;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;



/**   
 *  
 * @Description:  [Cube验证异常]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class AuthenticationCubeException extends ValidationCubeException {

	private static final long serialVersionUID = 4041513954371374923L;

	public AuthenticationCubeException(String message) {
		super(message);
	}
	public AuthenticationCubeException(String message, Object[] args){
		super(message,args);
	}
	public AuthenticationCubeException(String message,Throwable cause){
		super(message,cause);
	}
	public AuthenticationCubeException(String message, Object[] args, Throwable cause){
		super(message,args,cause);
	}

}
