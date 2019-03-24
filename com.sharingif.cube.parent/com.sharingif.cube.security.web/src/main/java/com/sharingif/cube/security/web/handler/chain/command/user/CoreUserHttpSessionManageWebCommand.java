package com.sharingif.cube.security.web.handler.chain.command.user;


import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestContext;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.web.user.IWebUserManage;

/**   
 *  
 * @Description:  [存储用户到HttpSession]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月21日 下午4:16:53]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月21日 下午4:16:53]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class CoreUserHttpSessionManageWebCommand extends AbstractHandlerMethodCommand {
	
	private IWebUserManage webUserManage;
	
	public IWebUserManage getWebUserManage() {
		return webUserManage;
	}
	public void setWebUserManage(IWebUserManage webUserManage) {
		this.webUserManage = webUserManage;
	}


	@Override
	public void execute(HandlerMethodContent content) throws CubeException {

		ICoreUser user = content.getObject(ICoreUser.class);

		HttpRequestContext<HttpRequest,HttpResponse> httpRequestContext = content.getRequestContext();
		
		webUserManage.persistenceUser(httpRequestContext.getRequest(), user);
	}

}
