package com.sharingif.cube.web.user;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpSession;
import com.sharingif.cube.core.user.ICoreUser;

/**   
 *  
 * @Description:  [管理web user]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月23日 下午3:12:07]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月23日 下午3:12:07]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class CoreUserHttpSessionManage implements IWebUserManage {

	@Override
	public void persistenceUser(HttpRequest request, ICoreUser user) {
		request.getSession(true).setAttribute(ICoreUser.CORE_USER, user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ICoreUser> T getUser(HttpRequest request) {
		HttpSession session = request.getSession(false);
		if(null == session)
			return null;
		
		Object obj = session.getAttribute(ICoreUser.CORE_USER);
		if(null != obj)
			return (T) obj;
		
		return null;
	}
	
}
