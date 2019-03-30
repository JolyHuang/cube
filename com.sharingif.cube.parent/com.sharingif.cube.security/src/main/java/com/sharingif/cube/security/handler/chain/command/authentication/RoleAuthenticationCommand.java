package com.sharingif.cube.security.handler.chain.command.authentication;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.authentication.role.IRoleAuthenticationHandler;

import java.util.List;

/**   
 *  
 * @Description:  [管理用户角色]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月17日 下午9:40:07]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月17日 下午9:40:07]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class RoleAuthenticationCommand extends AbstractHandlerMethodCommand {
	
	private List<IRoleAuthenticationHandler> roleAuthenticationHandlers;

	public List<IRoleAuthenticationHandler> getRoleAuthenticationHandlers() {
		return roleAuthenticationHandlers;
	}
	public void setRoleAuthenticationHandlers(List<IRoleAuthenticationHandler> roleAuthenticationHandlers) {
		this.roleAuthenticationHandlers = roleAuthenticationHandlers;
	}
	
	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		for(IRoleAuthenticationHandler roleAuthenticationHandler : roleAuthenticationHandlers){
			roleAuthenticationHandler.handleRole(content.getObject(ICoreUser.class));
		}
	}

}
