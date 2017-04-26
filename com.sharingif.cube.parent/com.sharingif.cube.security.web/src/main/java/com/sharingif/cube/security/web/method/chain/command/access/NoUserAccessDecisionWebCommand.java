package com.sharingif.cube.security.web.method.chain.command.access;

import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.security.web.access.INoUserHandler;
import com.sharingif.cube.web.method.chain.command.AbstractWebHandlerMethodCommand;

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
public class NoUserAccessDecisionWebCommand extends AbstractWebHandlerMethodCommand {
	
	private INoUserHandler noUserHandler;
	
	public INoUserHandler getNoUserHandler() {
		return noUserHandler;
	}
	public void setNoUserHandler(INoUserHandler noUserHandler) {
		this.noUserHandler = noUserHandler;
	}

	
	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		noUserHandler.handleNoUser(content.getRequest());
	}

}
