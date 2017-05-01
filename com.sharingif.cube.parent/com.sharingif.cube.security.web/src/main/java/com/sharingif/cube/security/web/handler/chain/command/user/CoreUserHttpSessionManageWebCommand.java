package com.sharingif.cube.security.web.handler.chain.command.user;


import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.web.handler.chain.command.AbstractWebHandlerMethodCommand;
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
public class CoreUserHttpSessionManageWebCommand extends AbstractWebHandlerMethodCommand {
	
	private IWebUserManage webUserManage;
	
	public IWebUserManage getWebUserManage() {
		return webUserManage;
	}
	public void setWebUserManage(IWebUserManage webUserManage) {
		this.webUserManage = webUserManage;
	}


	@Override
	public void execute(HttpHandlerMethodContent content) throws CubeException {
		webUserManage.persistenceUser(content.getRequest(), content.getObject(ICoreUser.class));
	}

}
