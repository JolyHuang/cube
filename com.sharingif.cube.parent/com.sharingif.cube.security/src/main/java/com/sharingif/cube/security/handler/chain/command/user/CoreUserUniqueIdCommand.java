package com.sharingif.cube.security.handler.chain.command.user;


import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.authentication.user.CoreUserUniqueIdHandler;

/**
 *
 * @Description:  [设置用户UniqueId]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月7日 下午5:32:46]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月7日 下午5:32:46]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class CoreUserUniqueIdCommand extends AbstractHandlerMethodCommand {
	
	private CoreUserUniqueIdHandler coreUserUniqueIdHandler;
	
	public CoreUserUniqueIdHandler getCoreUserUniqueIdHandler() {
		return coreUserUniqueIdHandler;
	}
	public void setCoreUserUniqueIdHandler(
			CoreUserUniqueIdHandler coreUserUniqueIdHandler) {
		this.coreUserUniqueIdHandler = coreUserUniqueIdHandler;
	}


	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		ICoreUser coreUser = content.getObject(ICoreUser.class);
		coreUserUniqueIdHandler.handleCoreUserUniqueId(coreUser);
	}

}
