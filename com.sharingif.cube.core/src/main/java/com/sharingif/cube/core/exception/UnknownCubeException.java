package com.sharingif.cube.core.exception;



/**   
 *  
 * @Description:  [未知异常]   
 * @Author:       [Joly]   
 * @CreateDate:   [2013年12月16日 下午8:42:48]   
 * @UpdateUser:   [Joly]   
 * @UpdateDate:   [2013年12月16日 下午8:42:48]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class UnknownCubeException extends CubeRuntimeException {

	
	private static final long serialVersionUID = -4774448022631370417L;
	
	private static final String SYSTEM_ERROR="system unknown error";
	
	public UnknownCubeException() {
		super(SYSTEM_ERROR);
	}
	
	public UnknownCubeException(Throwable cause) {
		super(SYSTEM_ERROR,cause);
	}

}
