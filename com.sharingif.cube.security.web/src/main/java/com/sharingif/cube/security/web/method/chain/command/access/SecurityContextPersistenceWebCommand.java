package com.sharingif.cube.security.web.method.chain.command.access;


import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.security.web.authentication.ISecurityContextPersistenceHandler;
import com.sharingif.cube.web.method.chain.command.AbstractWebHandlerMethodCommand;

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
public class SecurityContextPersistenceWebCommand extends AbstractWebHandlerMethodCommand {
	
	private ISecurityContextPersistenceHandler securityContextPersistenceHandler;
	
	public ISecurityContextPersistenceHandler getSecurityContextPersistenceHandler() {
		return securityContextPersistenceHandler;
	}

	public void setSecurityContextPersistenceHandler(
			ISecurityContextPersistenceHandler securityContextPersistenceHandler) {
		this.securityContextPersistenceHandler = securityContextPersistenceHandler;
	}

	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		
		securityContextPersistenceHandler.handleSecurityContextPersistence(content.getRequest(), content.getResponse());
        
	}
	
	

}
