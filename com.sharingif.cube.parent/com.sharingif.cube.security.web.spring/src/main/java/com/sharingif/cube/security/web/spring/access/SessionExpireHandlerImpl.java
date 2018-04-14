package com.sharingif.cube.security.web.spring.access;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.security.exception.validation.access.SessionExpireAccessDecisionCubeException;
import com.sharingif.cube.security.web.access.ISessionExpireHandler;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpSession;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import javax.servlet.http.HttpSession;

/**
 *
 * @Description:  [处理session失效]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年3月31日 下午4:27:50]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年3月31日 下午4:27:50]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SessionExpireHandlerImpl implements ISessionExpireHandler{
	
	private SessionRegistry sessionRegistry;
	
	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}
	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
	
	
	@Override
	public void handleUserExpire(HttpRequest request) throws SessionExpireAccessDecisionCubeException {
		
		SpringMVCHttpRequest httpRequest = (SpringMVCHttpRequest)request;

		SpringMVCHttpSession springMVCHttpSession = ((SpringMVCHttpSession)httpRequest.getSession(false));
		if(springMVCHttpSession == null) {
			return;
		}

		HttpSession session =  springMVCHttpSession.getHttpSession();

		SessionInformation info = sessionRegistry.getSessionInformation(session.getId());
		
		 if (null == info){
			 return;
		 }

        if (info.isExpired()) {
        	HttpSession oldSession = ((SpringMVCHttpSession)httpRequest.getSession(false)).getHttpSession();
    		
    		if(null != oldSession)
    			oldSession.invalidate();
    		
    		request.getSession(true);
        	throw new SessionExpireAccessDecisionCubeException();
        } else {
            sessionRegistry.refreshLastRequest(info.getSessionId());
        }
	}

}
