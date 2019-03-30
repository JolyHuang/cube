package com.sharingif.cube.security.handler.chain.command.authentication;

import com.sharingif.cube.components.channel.IChannelContext;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.authentication.AuthenticationHander;

import java.util.List;

/**
 *
 * @Description:  [用户安全验证]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月7日 下午9:12:26]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月7日 下午9:12:26]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SecurityAuthenticationCommand extends AbstractHandlerMethodCommand {
	
	private List<AuthenticationHander> authenticationHanders;

	public List<AuthenticationHander> getAuthenticationHanders() {
		return authenticationHanders;
	}
	public void setAuthenticationHanders(
			List<AuthenticationHander> authenticationHanders) {
		this.authenticationHanders = authenticationHanders;
	}

	
	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		for(AuthenticationHander authenticationHander : authenticationHanders){
			
			authenticationHander.handerAuthentication(content.findObject(ICoreUser.class), content.findObject(IChannelContext.class));
			
		}
	}

}
