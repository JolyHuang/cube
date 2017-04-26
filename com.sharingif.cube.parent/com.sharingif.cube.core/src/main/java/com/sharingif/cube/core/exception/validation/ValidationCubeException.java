package com.sharingif.cube.core.exception.validation;

import com.sharingif.cube.core.exception.CubeRuntimeException;


/**   
 *  
 * @Description:  [验证异常]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月16日 下午1:33:02]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class ValidationCubeException extends CubeRuntimeException {

	private static final long serialVersionUID = -1388451909429122349L;
	
	public ValidationCubeException(String message){
		super(message);
	}
	public ValidationCubeException(String message, Object[] args){
		super(message,args);
	}
	public ValidationCubeException(String message,Throwable cause){
		super(message,cause);
	}
	public ValidationCubeException(String message, Object[] args, Throwable cause){
		super(message,args,cause);
	}

}
