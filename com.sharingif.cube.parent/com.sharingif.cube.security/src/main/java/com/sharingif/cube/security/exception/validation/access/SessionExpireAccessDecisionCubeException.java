package com.sharingif.cube.security.exception.validation.access;

/**
 *
 * @Description:  [用户已失效]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年3月31日 下午1:24:42]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年3月31日 下午1:24:42]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SessionExpireAccessDecisionCubeException extends AccessDecisionCubeException {
	
	private static final long serialVersionUID = -2740679657182010917L;
	private static final String USER_EXPIRE="the session is invalid";

	public SessionExpireAccessDecisionCubeException() {
		super(USER_EXPIRE);
	}


}
