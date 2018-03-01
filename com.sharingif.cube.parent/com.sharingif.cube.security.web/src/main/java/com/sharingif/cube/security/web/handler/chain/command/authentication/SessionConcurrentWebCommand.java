package com.sharingif.cube.security.web.handler.chain.command.authentication;


import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.web.authentication.ISessionConcurrentHandler;

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
public class SessionConcurrentWebCommand extends AbstractHandlerMethodCommand {
	
	private ISessionConcurrentHandler sessionConcurrentHandler;
	
	public ISessionConcurrentHandler getSessionConcurrentHandler() {
		return sessionConcurrentHandler;
	}
	public void setSessionConcurrentHandler(ISessionConcurrentHandler sessionConcurrentHandler) {
		this.sessionConcurrentHandler = sessionConcurrentHandler;
	}


	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		HttpRequestContext<HttpRequest,HttpResponse> httpRequestContext = content.getRequestContext();

		ICoreUser coreUser = (ICoreUser)content.getReturnValue();
		sessionConcurrentHandler.handleSessionConcurrent(coreUser, httpRequestContext.getRequest(), httpRequestContext.getResponse());
	}

}
