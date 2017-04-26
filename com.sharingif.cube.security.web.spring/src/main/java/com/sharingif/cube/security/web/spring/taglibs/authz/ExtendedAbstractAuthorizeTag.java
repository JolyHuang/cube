package com.sharingif.cube.security.web.spring.taglibs.authz;


import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * A base class for an &lt;authorize&gt; tag that is independent of the tag rendering technology (JSP, Facelets).
 * It treats tag attributes as simple strings rather than strings that may contain expressions with the
 * exception of the "access" attribute, which is always expected to contain a Spring EL expression.
 * <p/>
 * Subclasses are expected to extract tag attribute values from the specific rendering technology, evaluate
 * them as expressions if necessary, and set the String-based attributes of this class.
 *
 * @author Francois Beausoleil
 * @author Luke Taylor
 * @author Rossen Stoyanchev
 * @author Rob Winch
 * @since 3.1.0
 * 
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月27日 下午12:46:54]   
 * @UpdateRemark: [
 * 					1.去掉从SecurityContextHolder获得Authentication改为session获得
 * 				]  
 * @Version:      [v1.0] 
 */
public abstract class ExtendedAbstractAuthorizeTag extends SpringSecurityContextTagSupport {

	private static final long serialVersionUID = -1888351584003401832L;
	
	private String access;
	private String url;
	private String method = "GET";

	/**
	 * This method allows subclasses to provide a way to access the ServletRequest
	 * according to the rendering technology.
	 */
	protected abstract ServletRequest getRequest();

	/**
	 * This method allows subclasses to provide a way to access the ServletResponse
	 * according to the rendering technology.
	 */
	protected abstract ServletResponse getResponse();

	/**
	 * This method allows subclasses to provide a way to access the ServletContext
	 * according to the rendering technology.
	 */
	protected abstract ServletContext getServletContext();

	/**
	 * Make an authorization decision by considering all &lt;authorize&gt; tag attributes.
	 * The following are valid combinations of attributes:
	 * <ul>
	 * <li>access</li>
	 * <li>url, method</li>
	 * </ul>
	 * The above combinations are mutually exclusive and evaluated in the given order.
	 *
	 * @return the result of the authorization decision
	 * @throws IOException
	 */
	public boolean authorize() throws IOException {
		boolean isAuthorized;

		if (StringUtils.hasText(getAccess())) {
			isAuthorized = authorizeUsingAccessExpression();

		}
		else if (StringUtils.hasText(getUrl())) {
			isAuthorized = authorizeUsingUrlCheck();

		}
		else {
			isAuthorized = false;

		}

		return isAuthorized;
	}

	/**
	 * Make an authorization decision based on a Spring EL expression. See the
	 * "Expression-Based Access Control" chapter in Spring Security for details on what
	 * expressions can be used.
	 *
	 * @return the result of the authorization decision
	 * @throws IOException
	 */
	public boolean authorizeUsingAccessExpression() throws IOException {
		if (getSecurityContext().getAuthentication() == null) {
			return false;
		}

		SecurityExpressionHandler<FilterInvocation> handler = getExpressionHandler();

		Expression accessExpression;
		try {
			accessExpression = handler.getExpressionParser().parseExpression(getAccess());

		}
		catch (ParseException e) {
			IOException ioException = new IOException();
			ioException.initCause(e);
			throw ioException;
		}

		return ExpressionUtils.evaluateAsBoolean(accessExpression,
				createExpressionEvaluationContext(handler));
	}

	/**
	 * Allows the {@code EvaluationContext} to be customized for variable lookup etc.
	 */
	protected EvaluationContext createExpressionEvaluationContext(
			SecurityExpressionHandler<FilterInvocation> handler) {
		FilterInvocation f = new FilterInvocation(getRequest(), getResponse(),
				new FilterChain() {
					public void doFilter(ServletRequest request, ServletResponse response)
							throws IOException, ServletException {
						throw new UnsupportedOperationException();
					}
				});

		return handler.createEvaluationContext(getSecurityContext().getAuthentication(), f);
	}

	/**
	 * Make an authorization decision based on the URL and HTTP method attributes. True is
	 * returned if the user is allowed to access the given URL as defined.
	 *
	 * @return the result of the authorization decision
	 * @throws IOException
	 */
	public boolean authorizeUsingUrlCheck() throws IOException {
		String contextPath = ((HttpServletRequest) getRequest()).getContextPath();
		Authentication currentUser = getSecurityContext().getAuthentication();
		return getPrivilegeEvaluator().isAllowed(contextPath, getUrl(), getMethod(),
				currentUser);
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = (method != null) ? method.toUpperCase() : null;
	}

	/*------------- Private helper methods  -----------------*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SecurityExpressionHandler<FilterInvocation> getExpressionHandler()
			throws IOException {
		ApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		Map<String, SecurityExpressionHandler> handlers = appContext
				.getBeansOfType(SecurityExpressionHandler.class);

		for (SecurityExpressionHandler h : handlers.values()) {
			if (FilterInvocation.class.equals(GenericTypeResolver.resolveTypeArgument(
					h.getClass(), SecurityExpressionHandler.class))) {
				return h;
			}
		}

		throw new IOException(
				"No visible WebSecurityExpressionHandler instance could be found in the application "
						+ "context. There must be at least one in order to support expressions in JSP 'authorize' tags.");
	}

	private WebInvocationPrivilegeEvaluator getPrivilegeEvaluator() throws IOException {
		WebInvocationPrivilegeEvaluator privEvaluatorFromRequest = (WebInvocationPrivilegeEvaluator) getRequest()
				.getAttribute(WebAttributes.WEB_INVOCATION_PRIVILEGE_EVALUATOR_ATTRIBUTE);
		if (privEvaluatorFromRequest != null) {
			return privEvaluatorFromRequest;
		}

		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		Map<String, WebInvocationPrivilegeEvaluator> wipes = ctx
				.getBeansOfType(WebInvocationPrivilegeEvaluator.class);

		if (wipes.size() == 0) {
			throw new IOException(
					"No visible WebInvocationPrivilegeEvaluator instance could be found in the application "
							+ "context. There must be at least one in order to support the use of URL access checks in 'authorize' tags.");
		}

		return (WebInvocationPrivilegeEvaluator) wipes.values().toArray()[0];
	}


}
