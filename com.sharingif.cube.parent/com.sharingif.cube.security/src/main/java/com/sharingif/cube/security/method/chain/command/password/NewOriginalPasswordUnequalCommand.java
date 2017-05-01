package com.sharingif.cube.security.method.chain.command.password;

import com.sharingif.cube.components.password.IOriginalPassword;
import com.sharingif.cube.components.password.IPassword;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;

/**
 * 新密码不能等于旧密码
 * 2015年11月23日 下午10:10:44
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class NewOriginalPasswordUnequalCommand extends AbstractHandlerMethodCommand {

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		IPassword password = content.getObject(IPassword.class);
		IOriginalPassword originalPassword = content.getObject(IOriginalPassword.class);
		
		if(password.getPassword().equals(originalPassword.getOriginalPassword())){
			throw new ValidationCubeException("The new password cannot be the same as the old one");
		}
	}

}
