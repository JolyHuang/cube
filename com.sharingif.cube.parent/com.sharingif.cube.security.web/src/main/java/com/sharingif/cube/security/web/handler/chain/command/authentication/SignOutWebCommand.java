package com.sharingif.cube.security.web.handler.chain.command.authentication;


import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.security.web.authentication.ISignOutHandler;
import com.sharingif.cube.web.handler.chain.command.AbstractWebHandlerMethodCommand;

/**
 *
 * @Description:  [用户安全退出命令]
 * @Author:       [Joly]
 * @CreateDate:   [2014年6月5日 上午9:47:32]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年6月5日 上午9:47:32]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SignOutWebCommand extends AbstractWebHandlerMethodCommand {
	
	private ISignOutHandler signOutHandler;

	public ISignOutHandler getSignOutHandler() {
		return signOutHandler;
	}
	public void setSignOutHandler(ISignOutHandler signOutHandler) {
		this.signOutHandler = signOutHandler;
	}

	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		
		signOutHandler.handleUserLogout(content.getRequest(), content.getResponse());
	}
	
	

}
