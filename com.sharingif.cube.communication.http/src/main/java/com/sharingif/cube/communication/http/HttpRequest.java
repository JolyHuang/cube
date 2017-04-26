package com.sharingif.cube.communication.http;

import java.util.Enumeration;
import java.util.Map;

/**
 * HttpServletRequest
 * 2016年12月29日 下午8:36:41
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface HttpRequest {
	
	/**
     * Returns the length, in bytes, of the request body 
     * and made available by the input stream, or -1 if the
     * length is not known. For HTTP servlets, same as the value
     * of the CGI variable CONTENT_LENGTH.
     *
     * @return		an integer containing the length of the 
     * 			request body or -1 if the length is not known
     *
     */
    public int getContentLength();
    
    /**
     * Returns the MIME type of the body of the request, or 
     * <code>null</code> if the type is not known. For HTTP servlets, 
     * same as the value of the CGI variable CONTENT_TYPE.
     *
     * @return		a <code>String</code> containing the name 
     *			of the MIME type of 
     * 			the request, or -1 if the type is not known
     *
     */
    public String getContentType();
	
	/**
     * Returns the value of a request parameter as a <code>String</code>,
     * or <code>null</code> if the parameter does not exist. Request parameters
     * are extra information sent with the request.  For HTTP servlets,
     * parameters are contained in the query string or posted form data.
     *
     * <p>You should only use this method when you are sure the
     * parameter has only one value. If the parameter might have
     * more than one value, use {@link #getParameterValues}.
     *
     * <p>If you use this method with a multivalued
     * parameter, the value returned is equal to the first value
     * in the array returned by <code>getParameterValues</code>.
     *
     * <p>If the parameter data was sent in the request body, such as occurs
     * with an HTTP POST request, then reading the body directly via {@link
     * #getInputStream} or {@link #getReader} can interfere
     * with the execution of this method.
     *
     * @param name 	a <code>String</code> specifying the 
     *			name of the parameter
     *
     * @return		a <code>String</code> representing the 
     *			single value of the parameter
     *
     * @see 		#getParameterValues
     *
     */
    public String getParameter(String name);
	
	/**
    *
    * Returns the value of the specified request header
    * as a <code>String</code>. If the request did not include a header
    * of the specified name, this method returns <code>null</code>.
    * The header name is case insensitive. You can use
    * this method with any request header.
    *
    * @param name		a <code>String</code> specifying the
    *				header name
    *
    * @return			a <code>String</code> containing the
    *				value of the requested
    *				header, or <code>null</code>
    *				if the request does not
    *				have a header of that name
    *
    */			

    /**
     * Stores an attribute in this request.
     * Attributes are reset between requests.  This method is most
     * often used in conjunction with {@link RequestDispatcher}.
     *
     * <p>Attribute names should follow the same conventions as
     * package names. Names beginning with <code>java.*</code>,
     * <code>javax.*</code>, and <code>com.sun.*</code>, are
     * reserved for use by Sun Microsystems.
     *<br> If the object passed in is null, the effect is the same as
     * calling {@link #removeAttribute}.
     * <br> It is warned that when the request is dispatched from the
     * servlet resides in a different web application by
     * <code>RequestDispatcher</code>, the object set by this method
     * may not be correctly retrieved in the caller servlet.
     *
     * @param name a <code>String</code> specifying 
     * the name of the attribute
     *
     * @param o the <code>Object</code> to be stored
     *
     */
    public void setAttribute(String name, Object o);
    
   public String getHeader(String name); 
   
   /**
   *
   * Returns all the values of the specified request header
   * as an <code>Enumeration</code> of <code>String</code> objects.
   *
   * <p>Some headers, such as <code>Accept-Language</code> can be sent
   * by clients as several headers each with a different value rather than
   * sending the header as a comma separated list.
   *
   * <p>If the request did not include any headers
   * of the specified name, this method returns an empty
   * <code>Enumeration</code>.
   * The header name is case insensitive. You can use
   * this method with any request header.
   *
   * @param name		a <code>String</code> specifying the
   *				header name
   *
   * @return			a <code>Enumeration</code> containing the
   *				values of the requested
   *				header, or <code>null</code>
   *				if the request does not
   *				have any headers of that name
   *
   */			
   public Enumeration<?> getHeaders(String name); 
	  
   /**
    *
    * Returns an enumeration of all the header names
	* this request contains. If the request has no
	* headers, this method returns an empty enumeration.
	*
	* <p>Some servlet containers do not allow do not allow
	* servlets to access headers using this method, in
	* which case this method returns <code>null</code>
	*
	* @return			an enumeration of all the
	*				header names sent with this
	*				request; if the request has
	*				no headers, an empty enumeration;
	*				if the servlet container does not
	*				allow servlets to use this method,
	*				<code>null</code>
	*
	*/
   public Enumeration<?> getHeaderNames();
	 
   /**
	 *
	 * Returns the name of the HTTP method with which this 
	 * request was made, for example, GET, POST, or PUT.
	 * Same as the value of the CGI variable REQUEST_METHOD.
	 *
	 * @return			a <code>String</code> 
	 *				specifying the name
	 *				of the method with which
	 *				this request was made
	 *
	 */
	public String getMethod();

	/**
	*
	* Returns the portion of the request URI that indicates the context
	* of the request.  The context path always comes first in a request
	* URI.  The path starts with a "/" character but does not end with a "/"
	* character.  For servlets in the default (root) context, this method
	* returns "".
	*
	*
	* @return		a <code>String</code> specifying the
	*			portion of the request URI that indicates the context
	*			of the request
	*
	*
	*/
	public String getContextPath();

	/**
	*
	* Returns the part of this request's URL from the protocol
	* name up to the query string in the first line of the HTTP request.
	* For example:
	*
	* <blockquote>
	* <table>
	* <tr align=left><th>First line of HTTP request<th>
	* <th>Returned Value
	* <tr><td>POST /some/path.html HTTP/1.1<td><td>/some/path.html
	* <tr><td>GET http://foo.bar/a.html HTTP/1.0
	* <td><td>http://foo.bar/a.html
	* <tr><td>HEAD /xyz?a=b HTTP/1.1<td><td>/xyz
	* </table>
	* </blockquote>
	*
	* <p>To reconstruct an URL with a scheme and host, use
	* {@link HttpUtils#getRequestURL}.
	*
	* @return		a <code>String</code> containing
	*			the part of the URL from the 
	*			protocol name up to the query string
	*
	* @see		HttpUtils#getRequestURL
	*
	*/
	public String getRequestURI();
	
	/**
     * Reconstructs the URL the client used to make the request.
     * The returned URL contains a protocol, server name, port
     * number, and server path, but it does not include query
     * string parameters.
     *
     * <p>If this request has been forwarded using
     * {@link javax.servlet.RequestDispatcher#forward}, the server path in the
     * reconstructed URL must reflect the path used to obtain the
     * RequestDispatcher, and not the server path specified by the client.
     *
     * <p>Because this method returns a <code>StringBuffer</code>,
     * not a string, you can modify the URL easily, for example,
     * to append query parameters.
     *
     * <p>This method is useful for creating redirect messages
     * and for reporting errors.
     *
     * @return		a <code>StringBuffer</code> object containing
     *			the reconstructed URL
     */
    public StringBuffer getRequestURL();
	
	/**
     * Returns the query string that is contained in the request
     * URL after the path. This method returns <code>null</code>
     * if the URL does not have a query string. Same as the value
     * of the CGI variable QUERY_STRING. 
     *
     * @return		a <code>String</code> containing the query
     *			string or <code>null</code> if the URL 
     *			contains no query string. The value is not
     *			decoded by the container.
     */
    public String getQueryString();
	
	/**
    *
    * Returns the current <code>HttpSession</code>
    * associated with this request or, if if there is no
    * current session and <code>create</code> is true, returns 
    * a new session.
    *
    * <p>If <code>create</code> is <code>false</code>
    * and the request has no valid <code>HttpSession</code>,
    * this method returns <code>null</code>.
    *
    * <p>To make sure the session is properly maintained,
    * you must call this method before 
    * the response is committed.
    *
    *
    *
    *
    * @param		<code>true</code> to create
    *			a new session for this request if necessary; 
    *			<code>false</code> to return <code>null</code>
    *			if there's no current session
    *			
    *
    * @return 		the <code>HttpSession</code> associated 
    *			with this request or <code>null</code> if
    * 			<code>create</code> is <code>false</code>
    *			and the request has no valid session
    *
    * @see	#getSession()
    *
    *
    */
   public HttpSession getSession(boolean create);
   
   /**
   *
   * Returns the current session associated with this request,
   * or if the request does not have a session, creates one.
   * 
   * @return		the <code>HttpSession</code> associated
   *			with this request
   *
   * @see	#getSession(boolean)
   *
   */
  public HttpSession getSession();
  
  /**
   * Returns a java.util.Map of the parameters of this request.
   * 
   * <p>Request parameters are extra information sent with the request.
   * For HTTP servlets, parameters are contained in the query string or
   * posted form data.
   *
   * @return an immutable java.util.Map containing parameter names as 
   * keys and parameter values as map values. The keys in the parameter
   * map are of type String. The values in the parameter map are of type
   * String array.
   */
  public Map<String, String[]> getParameterMap();
	
}
