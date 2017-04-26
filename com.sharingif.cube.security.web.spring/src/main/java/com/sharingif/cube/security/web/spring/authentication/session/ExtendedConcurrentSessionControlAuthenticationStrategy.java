package com.sharingif.cube.security.web.spring.authentication.session;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.sharingif.cube.security.exception.validation.authentication.AuthenticationCubeException;

/**
 * 扩展ConcurrentSessionControlAuthenticationStrategy{@link org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy}，
 * 1.重写onAuthentication方法，去掉同一浏览器多次登录判断。登录会走session重置重绑流程，同一浏览器多次登录判断会失效导致一个浏览器同一用户多次登录2个session有效。
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
	public void onAuthentication(Authentication authentication,
			HttpServletRequest request, HttpServletResponse response) {
		
		final List<SessionInformation> sessions = sessionRegistry.getAllSessions(
				authentication.getPrincipal(), false);

		int sessionCount = sessions.size();
		int allowedSessions = getMaximumSessionsForThisUser(authentication);

		if (sessionCount < allowedSessions) {
			// They haven't got too many login sessions running at present
			return;
		}

		if (allowedSessions == -1) {
			// We permit unlimited logins
			return;
		}

//		if (sessionCount == allowedSessions) {
//			HttpSession session = request.getSession(false);
//
//			if (session != null) {
//				// Only permit it though if this request is associated with one of the
//				// already registered sessions
//				for (SessionInformation si : sessions) {
//					if (si.getSessionId().equals(session.getId())) {
//						return;
//					}
//				}
//			}
//			// If the session is null, a new one will be created by the parent class,
//			// exceeding the allowed number
//		}

		allowableSessionsExceeded(sessions, allowedSessions, sessionRegistry);
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
