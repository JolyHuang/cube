package com.sharingif.cube.security.exception.validation.authentication;

/**
 *
 * @Description:  [用户重复登录异常]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月7日 上午11:15:45]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月7日 上午11:15:45]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class RepeatLoginAuthenticationCubeException extends AuthenticationCubeException {

	private static final long serialVersionUID = -2934289256072971511L;
	private static final String REPEAT_LOGIN="The user is logged in, cannot repeat login";
	
	public RepeatLoginAuthenticationCubeException() {
		super(REPEAT_LOGIN);
	}

}
