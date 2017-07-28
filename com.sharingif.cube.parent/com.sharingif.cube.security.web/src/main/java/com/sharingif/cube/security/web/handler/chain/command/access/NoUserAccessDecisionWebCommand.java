package com.sharingif.cube.security.web.handler.chain.command.access;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestInfo;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.security.web.access.INoUserHandler;

/**
 *
 * @Description:  [处理空用户]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月1日 下午7:00:38]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月1日 下午7:00:38]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class NoUserAccessDecisionWebCommand extends AbstractHandlerMethodCommand {
	
	private INoUserHandler noUserHandler;
	
	public INoUserHandler getNoUserHandler() {
		return noUserHandler;
	}
	public void setNoUserHandler(INoUserHandler noUserHandler) {
		this.noUserHandler = noUserHandler;
	}

	
	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		HttpRequestInfo<HttpRequest,HttpResponse> httpRequestInfo = content.getRequestInfo();
		
		noUserHandler.handleNoUser(httpRequestInfo.getRequest());
	}

}
