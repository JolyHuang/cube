package com.sharingif.cube.core.exception.validation;


/**   
 *  
 * @Description:  [Token验证异常]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年2月21日 下午3:51:02]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年2月21日 下午3:51:02]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class TokenValidationCubeException extends ValidationCubeException {

	private static final long serialVersionUID = 1155429869604675195L;

	public TokenValidationCubeException(String message){
		super(message);
	}

}
