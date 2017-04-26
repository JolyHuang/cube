package com.sharingif.cube.web.method.chain.command.token;

import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.components.token.IToken;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.web.components.token.WebTokenManager;
import com.sharingif.cube.web.method.chain.command.AbstractWebHandlerMethodCommand;

/**
 *
 * @Description:  [生成token，并保存到sesseion]
 * @Author:       [Joly]
 * @CreateDate:   [2014年5月29日 下午11:33:25]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年5月29日 下午11:33:25]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class CreateTokenWebCommand extends AbstractWebHandlerMethodCommand {
	
	private WebTokenManager webTokenManager;

	public WebTokenManager getWebTokenManager() {
		return webTokenManager;
	}
	public void setWebTokenManager(WebTokenManager webTokenManager) {
		this.webTokenManager = webTokenManager;
	}

	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		IToken token = webTokenManager.generateToken(content.getRequest());
		
		content.changeArgsValue(token);
	}

}
