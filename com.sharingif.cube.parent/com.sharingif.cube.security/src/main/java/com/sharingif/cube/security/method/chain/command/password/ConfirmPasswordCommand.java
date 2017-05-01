package com.sharingif.cube.security.method.chain.command.password;

import com.sharingif.cube.components.password.IConfirmPassword;
import com.sharingif.cube.components.password.IPassword;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.security.authentication.password.IConfirmPasswordAuthentication;

/**
 *
 * @Description:  [处理两次密码对比]
 * @Author:       [Joly]
 * @CreateDate:   [2014年5月26日 上午11:04:59]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年5月26日 上午11:04:59]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class ConfirmPasswordCommand extends AbstractHandlerMethodCommand {
	
	private IConfirmPasswordAuthentication confirmPasswordAuthentication;

	public IConfirmPasswordAuthentication getConfirmPasswordAuthentication() {
		return confirmPasswordAuthentication;
	}
	public void setConfirmPasswordAuthentication(IConfirmPasswordAuthentication confirmPasswordAuthentication) {
		this.confirmPasswordAuthentication = confirmPasswordAuthentication;
	}

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		IPassword password = content.getObject(IPassword.class);
		IConfirmPassword confirmPassword = content.getObject(IConfirmPassword.class);
		
		confirmPasswordAuthentication.confirmPasswordAuthentication(password.getPassword(), confirmPassword);
	}

}
