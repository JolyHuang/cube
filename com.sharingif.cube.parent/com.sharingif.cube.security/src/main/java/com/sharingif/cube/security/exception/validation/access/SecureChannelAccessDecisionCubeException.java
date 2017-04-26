package com.sharingif.cube.security.exception.validation.access;



/**   
 *  
 * @Description:  [https渠道异常]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月24日 下午4:35:59]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月24日 下午4:35:59]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class SecureChannelAccessDecisionCubeException extends AccessDecisionCubeException {

	private static final long serialVersionUID = 6645761297712377783L;
	
	private static final String SECURE_CHANNEL_ACCESS_DECISION_ERROR="secure channel access decision error";
	
	public SecureChannelAccessDecisionCubeException() {
		super(SECURE_CHANNEL_ACCESS_DECISION_ERROR);
	}


}
