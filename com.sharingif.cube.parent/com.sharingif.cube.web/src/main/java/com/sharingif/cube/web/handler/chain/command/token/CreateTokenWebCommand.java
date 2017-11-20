package com.sharingif.cube.web.handler.chain.command.token;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.components.token.IToken;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.web.components.token.WebTokenManager;

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
public class CreateTokenWebCommand extends AbstractHandlerMethodCommand {
	
	private WebTokenManager webTokenManager;

	public WebTokenManager getWebTokenManager() {
		return webTokenManager;
	}
	public void setWebTokenManager(WebTokenManager webTokenManager) {
		this.webTokenManager = webTokenManager;
	}

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		HttpRequestContext<HttpRequest,HttpResponse> httpRequestContext = content.getRequestContext();

		IToken token = webTokenManager.generateToken(httpRequestContext.getRequest());
		
		content.changeArgsValue(token);
	}

}
