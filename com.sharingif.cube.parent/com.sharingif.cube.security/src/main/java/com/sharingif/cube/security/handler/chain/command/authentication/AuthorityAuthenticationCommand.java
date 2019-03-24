package com.sharingif.cube.security.handler.chain.command.authentication;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.authentication.authority.IAuthorityAuthenticationHandler;

import java.util.List;

/**   
 *  
 * @Description:  [权限管理]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月17日 下午1:26:39]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月17日 下午1:26:39]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class AuthorityAuthenticationCommand extends AbstractHandlerMethodCommand {
	
	private List<IAuthorityAuthenticationHandler<? super ICoreUser>> authorityAuthenticationHandlers;

	public List<IAuthorityAuthenticationHandler<? super ICoreUser>> getAuthorityAuthenticationHandlers() {
		return authorityAuthenticationHandlers;
	}
	public void setAuthorityAuthenticationHandlers(
			List<IAuthorityAuthenticationHandler<? super ICoreUser>> authorityAuthenticationHandlers) {
		this.authorityAuthenticationHandlers = authorityAuthenticationHandlers;
	}
	
	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		for(IAuthorityAuthenticationHandler<? super ICoreUser> authorityAuthenticationHandler : authorityAuthenticationHandlers){
			authorityAuthenticationHandler.handleAuthorities(content.getObject(ICoreUser.class));
		}
	}

}
