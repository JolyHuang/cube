package com.sharingif.cube.security.exception.validation.access;

/**
 *
 * @Description:  [没有用户]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年3月31日 下午4:08:04]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年3月31日 下午4:08:04]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class NoUserAccessDecisionCubeException extends AccessDecisionCubeException {
	
	private static final long serialVersionUID = -4668649710815798770L;
	
	private static final String NO_USER="the user is null";
	
	public NoUserAccessDecisionCubeException() {
		super(NO_USER);
	}

}
