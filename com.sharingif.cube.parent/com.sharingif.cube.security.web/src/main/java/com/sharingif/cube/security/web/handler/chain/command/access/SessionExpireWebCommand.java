package com.sharingif.cube.security.web.handler.chain.command.access;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.security.web.access.ISessionExpireHandler;
import com.sharingif.cube.web.handler.chain.command.AbstractWebHandlerMethodCommand;

/**
 *
 * @Description:  [处理用户失效]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月2日 下午4:03:57]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月2日 下午4:03:57]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SessionExpireWebCommand extends AbstractWebHandlerMethodCommand {
	
	private ISessionExpireHandler sessionExpireHandler;
	
	public ISessionExpireHandler getSessionExpireHandler() {
		return sessionExpireHandler;
	}
	public void setSessionExpireHandler(ISessionExpireHandler sessionExpireHandler) {
		this.sessionExpireHandler = sessionExpireHandler;
	}


	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		sessionExpireHandler.handleUserExpire(content.getRequest());
	}

}
