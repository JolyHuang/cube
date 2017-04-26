package com.sharingif.cube.security.authentication.password;

import com.sharingif.cube.components.password.IConfirmPassword;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;

/**
 *
 * @Description:  [验证确认密码]
 * @Author:       [Joly]
 * @CreateDate:   [2014年5月26日 上午10:44:36]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年5月26日 上午10:44:36]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class ConfirmPasswordAuthenticationImpl implements IConfirmPasswordAuthentication {

	@Override
	public void confirmPasswordAuthentication(String password, IConfirmPassword confirmPassword)throws ValidationCubeException {
		matchConfirmPassword(password, confirmPassword.getConfirmPassword());
		clearConfirmPassword(confirmPassword);
	}
	
	protected void matchConfirmPassword(String password, String confirmPassword) {
		if(!password.equals(confirmPassword))
			throw new ValidationCubeException("Passwords do not match");
	}
	
	/**
	 * 确认密码验证成功后删除,防止看到明文密码
	 * @param confirmPassword
	 */
	protected void clearConfirmPassword(IConfirmPassword confirmPassword) {
		confirmPassword.setConfirmPassword(null);
	}

}
