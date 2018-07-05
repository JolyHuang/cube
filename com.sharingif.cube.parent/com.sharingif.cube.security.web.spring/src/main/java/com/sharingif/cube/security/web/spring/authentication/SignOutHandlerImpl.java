package com.sharingif.cube.security.web.spring.authentication;


import javax.servlet.http.HttpSession;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.security.web.authentication.ISignOutHandler;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpResponse;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpSession;

/**
 *
 * @Description:  [用户安全退出]
 * @Author:       [Joly]
 * @CreateDate:   [2014年6月5日 上午9:30:12]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年6月5日 上午9:30:12]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SignOutHandlerImpl implements ISignOutHandler {
	
	private SessionRegistry sessionRegistry;
	private LogoutHandler logoutHandler;
	
	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}
	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
	public LogoutHandler getLogoutHandler() {
		return logoutHandler;
	}
	public void setLogoutHandler(LogoutHandler logoutHandler) {
		this.logoutHandler = logoutHandler;
	}

	@Override
	public void handleUserLogout(HttpRequest request, HttpResponse response) {
		
		SpringMVCHttpRequest httpRequest = (SpringMVCHttpRequest)request;
		SpringMVCHttpResponse httpResponse = (SpringMVCHttpResponse)response;
		
		HttpSession session = ((SpringMVCHttpSession)request.getSession(false)).getHttpSession();

		if(null == session)
			return;
			
        SessionInformation info = sessionRegistry.getSessionInformation(session.getId());
        
        // 使session失效
        if(null != info){
            info.expireNow();
        }
        
        // 重置session
        session.invalidate();
        
        // 安全退出
        logoutHandler.logout(httpRequest.getHttpServletRequest(), httpResponse.getHttpServletResponse(), null);

	}
	
	
	

}
