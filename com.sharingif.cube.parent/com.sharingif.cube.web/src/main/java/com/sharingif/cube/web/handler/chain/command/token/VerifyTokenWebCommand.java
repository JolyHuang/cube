package com.sharingif.cube.web.handler.chain.command.token;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestInfo;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.web.components.token.WebTokenManager;

/**   
 *  
 * @Description:  [验证token]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年1月30日 下午2:37:28]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年1月30日 下午2:37:28]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class VerifyTokenWebCommand extends AbstractHandlerMethodCommand {
	
	private WebTokenManager webTokenManager;

	public WebTokenManager getWebTokenManager() {
		return webTokenManager;
	}
	public void setWebTokenManager(WebTokenManager webTokenManager) {
		this.webTokenManager = webTokenManager;
	}
	
	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		HttpRequestInfo<HttpRequest,HttpResponse> httpRequestInfo = content.getRequestInfo();
		
		webTokenManager.verifyToken(httpRequestInfo.getRequest());
	}
	
	
}
