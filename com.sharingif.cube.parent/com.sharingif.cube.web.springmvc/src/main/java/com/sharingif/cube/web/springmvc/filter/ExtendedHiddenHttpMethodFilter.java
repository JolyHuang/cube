package com.sharingif.cube.web.springmvc.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * {@link javax.servlet.Filter} that converts posted method parameters into HTTP methods,
 * retrievable via {@link HttpServletRequest#getMethod()}. Since browsers currently only
 * support GET and POST, a common technique - used by the Prototype library, for instance -
 * is to use a normal POST with an additional hidden form field ({@code _method})
 * to pass the "real" HTTP method along. This filter reads that parameter and changes
 * the {@link HttpServletRequestWrapper#getMethod()} return value accordingly.
 *
 * <p>The name of the request parameter defaults to {@code _method}, but can be
 * adapted via the {@link #setMethodParam(String) methodParam} property.
 *
 * <p><b>NOTE: This filter needs to run after multipart processing in case of a multipart
 * POST request, due to its inherent need for checking a POST body parameter.</b>
 * So typically, put a Spring {@link org.springframework.web.multipart.support.MultipartFilter}
 * <i>before</i> this HiddenHttpMethodFilter in your {@code web.xml} filter chain.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
public class ExtendedHiddenHttpMethodFilter extends HiddenHttpMethodFilter{

	private String methodParam = DEFAULT_METHOD_PARAM;


	/**
	 * Set the parameter name to look for HTTP methods.
	 * @see #DEFAULT_METHOD_PARAM
	 */
	public void setMethodParam(String methodParam) {
		super.setMethodParam(methodParam);
		Assert.hasText(methodParam, "'methodParam' must not be empty");
		this.methodParam = methodParam;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		HttpServletRequest requestToUse = request;
		String paramValue = request.getParameter(this.methodParam);
		if (StringUtils.hasLength(paramValue)) {
			String method = paramValue.toUpperCase(Locale.ENGLISH);
			requestToUse = new HttpMethodRequestWrapper(request, method);
		}
		
		filterChain.doFilter(requestToUse, response);
	}


	/**
	 * Simple {@link HttpServletRequest} wrapper that returns the supplied method for
	 * {@link HttpServletRequest#getMethod()}.
	 */
	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

		private final String method;

		public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}
	
}
