package com.sharingif.cube.security.web.spring.authentication.session;

import java.util.List;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.sharingif.cube.security.exception.validation.authentication.AuthenticationCubeException;

/**
 * 扩展ConcurrentSessionControlAuthenticationStrategy{@link org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy}，
 * 2.重写allowableSessionsExceeded方法，改变异常类型
 * 2015年8月31日 下午11:52:24
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ExtendedConcurrentSessionControlAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {
	
	private final SessionRegistry sessionRegistry;
	
	public ExtendedConcurrentSessionControlAuthenticationStrategy(SessionRegistry sessionRegistry) {
		super(sessionRegistry);
		this.sessionRegistry = sessionRegistry;
	}
	
	@Override
	protected void allowableSessionsExceeded(List<SessionInformation> sessions,
			int allowableSessions, SessionRegistry registry)
			throws SessionAuthenticationException {
		
		try {
			super.allowableSessionsExceeded(sessions, allowableSessions, registry);
		} catch (SessionAuthenticationException e) {
			throw new AuthenticationCubeException("Maximum sessions for this principal exceeded", new Object[] {Integer.valueOf(allowableSessions)});
		}
	}
	
	
   
}
