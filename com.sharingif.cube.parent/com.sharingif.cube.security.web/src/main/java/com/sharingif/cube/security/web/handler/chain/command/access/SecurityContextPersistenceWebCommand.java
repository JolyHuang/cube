package com.sharingif.cube.security.web.handler.chain.command.access;


import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.security.web.authentication.ISecurityContextPersistenceHandler;

/**   
 *  
 * @Description:  [保存SecurityContext到session]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月26日 下午6:58:30]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月26日 下午6:58:30]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class SecurityContextPersistenceWebCommand extends AbstractHandlerMethodCommand {
	
	private ISecurityContextPersistenceHandler securityContextPersistenceHandler;
	
	public ISecurityContextPersistenceHandler getSecurityContextPersistenceHandler() {
		return securityContextPersistenceHandler;
	}

	public void setSecurityContextPersistenceHandler(
			ISecurityContextPersistenceHandler securityContextPersistenceHandler) {
		this.securityContextPersistenceHandler = securityContextPersistenceHandler;
	}

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		HttpRequestContext<HttpRequest,HttpResponse> httpRequestContext = content.getRequestContext();
		
		securityContextPersistenceHandler.handleSecurityContextPersistence(httpRequestContext.getRequest(), httpRequestContext.getResponse());
        
	}
	
	

}
