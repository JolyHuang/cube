package com.sharingif.cube.security.web.handler.chain.command.authentication;


import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestInfo;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.security.web.authentication.ISignOutHandler;

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
public class SignOutWebCommand extends AbstractHandlerMethodCommand {
	
	private ISignOutHandler signOutHandler;

	public ISignOutHandler getSignOutHandler() {
		return signOutHandler;
	}
	public void setSignOutHandler(ISignOutHandler signOutHandler) {
		this.signOutHandler = signOutHandler;
	}

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		HttpRequestInfo<HttpRequest,HttpResponse> httpRequestInfo = content.getRequestInfo();
		
		signOutHandler.handleUserLogout(httpRequestInfo.getRequest(), httpRequestInfo.getResponse());
	}
	
	

}
