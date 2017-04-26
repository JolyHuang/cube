package com.sharingif.cube.security.authentication.password;

import com.sharingif.cube.components.password.IConfirmPassword;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;

/**
 *
 * @Description:  [处理密码确认]
 * @Author:       [Joly]
 * @CreateDate:   [2014年5月26日 上午10:39:49]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年5月26日 上午10:39:49]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public interface IConfirmPasswordAuthentication {
	
	/**
	 * 处理密码确认
	 * @param password : 当前密码
	 * @param confirmPassword : 确认密码
	 * @throws ValidationCubeException
	 */
	void confirmPasswordAuthentication(String password, IConfirmPassword confirmPassword)throws ValidationCubeException;

}
