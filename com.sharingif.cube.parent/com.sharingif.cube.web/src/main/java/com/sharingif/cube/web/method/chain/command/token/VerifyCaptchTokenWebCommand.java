package com.sharingif.cube.web.method.chain.command.token;

import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.validation.TokenValidationCubeException;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.web.components.token.WebTokenManager;
import com.sharingif.cube.web.method.chain.command.AbstractWebHandlerMethodCommand;

/**   
 *  
 * @Description:  [验证Captch token]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年1月30日 下午2:37:28]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年1月30日 下午2:37:28]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class VerifyCaptchTokenWebCommand extends AbstractWebHandlerMethodCommand {
	
	private WebTokenManager webTokenManager;

	public WebTokenManager getWebTokenManager() {
		return webTokenManager;
	}
	public void setWebTokenManager(WebTokenManager webTokenManager) {
		this.webTokenManager = webTokenManager;
	}

	/**
	 * 当token为验证码时抛出ValidationCubeException，跳回原页面显示验证码错误
	 * @param tokenVerifyFailure
	 * @return
	 */
	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		try {
			webTokenManager.verifyToken(content.getRequest());
		} catch (TokenValidationCubeException e) {
			throw new ValidationCubeException(e.getMessage());
		}
	}
	
	
}
