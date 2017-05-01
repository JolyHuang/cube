package com.sharingif.cube.security.method.chain.command.password;

import com.sharingif.cube.components.password.IOriginalPassword;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.authentication.password.IOriginalPasswordHandler;

/**
 *
 * @Description:  [密码修改]
 * @Author:       [Joly]
 * @CreateDate:   [2014年6月4日 下午4:15:30]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年6月4日 下午4:15:30]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class OriginalPasswordCommand extends AbstractHandlerMethodCommand {

	private IOriginalPasswordHandler originalPasswordHandler;
	
	public IOriginalPasswordHandler getOriginalPasswordHandler() {
		return originalPasswordHandler;
	}
	public void setOriginalPasswordHandler(IOriginalPasswordHandler originalPasswordHandler) {
		this.originalPasswordHandler = originalPasswordHandler;
	}

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		IOriginalPassword originalPassword = content.getObject(IOriginalPassword.class);
		String username = content.getObject(ICoreUser.class).getUsername();
		
		originalPasswordHandler.handleOriginalPassword(username, originalPassword);
	}

}
