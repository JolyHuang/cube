package com.sharingif.cube.security.method.chain.command.user;

import com.sharingif.cube.components.password.IPassword;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;

/**
 *
 * @Description:  [用户验证后移除用户密码]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月7日 下午5:33:00]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月7日 下午5:33:00]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class RemoveUserPasswordCommand extends AbstractHandlerMethodCommand {

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		content.findObject(IPassword.class).setPassword(null);
	}
	

}
