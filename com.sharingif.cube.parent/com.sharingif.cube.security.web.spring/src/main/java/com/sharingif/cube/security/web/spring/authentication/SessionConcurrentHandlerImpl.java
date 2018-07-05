package com.sharingif.cube.security.web.spring.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.components.password.IPassword;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.exception.validation.authentication.AuthenticationCubeException;
import com.sharingif.cube.security.web.authentication.ISessionConcurrentHandler;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpRequest;
import com.sharingif.cube.web.springmvc.http.SpringMVCHttpResponse;

/**
 *
 * @Description:  [控制session并发]
 * @Author:       [Joly_Huang]
 * @CreateDate:   [2014年4月4日 上午11:26:40]
 * @UpdateUser:   [Joly_Huang]
 * @UpdateDate:   [2014年4月4日 上午11:26:40]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class SessionConcurrentHandlerImpl implements ISessionConcurrentHandler {
	
	private SessionAuthenticationStrategy sessionAuthenticationStrategy;
	
	public SessionAuthenticationStrategy getSessionAuthenticationStrategy() {
		return sessionAuthenticationStrategy;
	}
	public void setSessionAuthenticationStrategy(
			SessionAuthenticationStrategy sessionAuthenticationStrategy) {
		this.sessionAuthenticationStrategy = sessionAuthenticationStrategy;
	}



	@Override
	public void handleSessionConcurrent(ICoreUser coreUser, HttpRequest request, HttpResponse response) throws AuthenticationCubeException {
		sessionAuthenticationStrategy.onAuthentication(
				new UsernamePasswordAuthenticationToken(
						coreUser
						,((IPassword)coreUser).getPassword())
						,((SpringMVCHttpRequest)request).getHttpServletRequest()
						,((SpringMVCHttpResponse)response).getHttpServletResponse()
		);
	}

}
