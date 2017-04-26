package com.sharingif.cube.security.web.spring.taglibs.authz;


import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.TextEscapeUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.web.util.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * An {@link javax.servlet.jsp.tagext.Tag} implementation that allows convenient access to the current
 * <code>Authentication</code> object.
 * <p>
 * Whilst JSPs can access the <code>SecurityContext</code> directly, this tag avoids handling <code>null</code> conditions.
 *
 * @author Thomas Champagne
 */
public class ExtendedAuthenticationTag extends SpringSecurityContextTagSupport {
	
	// ~ Instance fields
	// ================================================================================================

	private static final long serialVersionUID = -476070548881701333L;
	
	private String var;
	private String property;
	private int scope;
	private boolean scopeSpecified;
	private boolean htmlEscape = true;

	// ~ Methods
	// ========================================================================================================

	public ExtendedAuthenticationTag() {
		init();
	}

	// resets local state
	private void init() {
		var = null;
		scopeSpecified = false;
		scope = PageContext.PAGE_SCOPE;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setProperty(String operation) {
		this.property = operation;
	}

	public void setScope(String scope) {
		this.scope = TagUtils.getScope(scope);
		this.scopeSpecified = true;
	}

	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public int doEndTag() throws JspException {
		Object result = null;
		// determine the value by...
		if (property != null) {
			if ((SecurityContextHolder.getContext() == null)
					|| !(SecurityContextHolder.getContext() instanceof SecurityContext)
					|| (SecurityContextHolder.getContext().getAuthentication() == null)) {
				return Tag.EVAL_PAGE;
			}

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth.getPrincipal() == null) {
				return Tag.EVAL_PAGE;
			}

			try {
				BeanWrapperImpl wrapper = new BeanWrapperImpl(auth);
				result = wrapper.getPropertyValue(property);
			}
			catch (BeansException e) {
				throw new JspException(e);
			}
		}

		if (var != null) {
			/*
			 * Store the result, letting an IllegalArgumentException propagate back if the
			 * scope is invalid (e.g., if an attempt is made to store something in the
			 * session without any HttpSession existing).
			 */
			if (result != null) {
				pageContext.setAttribute(var, result, scope);
			}
			else {
				if (scopeSpecified) {
					pageContext.removeAttribute(var, scope);
				}
				else {
					pageContext.removeAttribute(var);
				}
			}
		}
		else {
			if (htmlEscape) {
				writeMessage(TextEscapeUtils.escapeEntities(String.valueOf(result)));
			}
			else {
				writeMessage(String.valueOf(result));
			}
		}
		return EVAL_PAGE;
	}

	protected void writeMessage(String msg) throws JspException {
		try {
			pageContext.getOut().write(String.valueOf(msg));
		}
		catch (IOException ioe) {
			throw new JspException(ioe);
		}
	}

	/**
	 * Set HTML escaping for this tag, as boolean value.
	 */
	public void setHtmlEscape(String htmlEscape) throws JspException {
		this.htmlEscape = Boolean.valueOf(htmlEscape);
	}

	/**
	 * Return the HTML escaping setting for this tag, or the default setting if not
	 * overridden.
	 */
	protected boolean isHtmlEscape() {
		return htmlEscape;
	}

}
