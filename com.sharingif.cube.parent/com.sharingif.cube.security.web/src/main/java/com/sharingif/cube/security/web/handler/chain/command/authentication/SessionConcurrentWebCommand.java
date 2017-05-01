package com.sharingif.cube.security.web.handler.chain.command.authentication;


import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.web.authentication.ISessionConcurrentHandler;
import com.sharingif.cube.web.handler.chain.command.AbstractWebHandlerMethodCommand;

/**
 *
 * @Description:  [控制session并发]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月4日 上午11:47:09]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月4日 上午11:47:09]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SessionConcurrentWebCommand extends AbstractWebHandlerMethodCommand {
	
	private ISessionConcurrentHandler sessionConcurrentHandler;
	
	public ISessionConcurrentHandler getSessionConcurrentHandler() {
		return sessionConcurrentHandler;
	}
	public void setSessionConcurrentHandler(ISessionConcurrentHandler sessionConcurrentHandler) {
		this.sessionConcurrentHandler = sessionConcurrentHandler;
	}


	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		sessionConcurrentHandler.handleSessionConcurrent(content.findObject(ICoreUser.class), content.getRequest(),content.getResponse());
	}

}
