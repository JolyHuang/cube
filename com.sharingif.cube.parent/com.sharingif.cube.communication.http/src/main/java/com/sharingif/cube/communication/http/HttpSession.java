package com.sharingif.cube.communication.http;

import java.util.Enumeration;

/**
 * HttpSession
 * 2016年12月29日 下午8:38:05
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface HttpSession {

	/**
    *
    * Returns a string containing the unique identifier assigned 
    * to this session. The identifier is assigned 
    * by the servlet container and is implementation dependent.
    * 
    * @return				a string specifying the identifier
    *					assigned to this session
    *
    * @exeption IllegalStateException	if this method is called on an
    *					invalidated session
    *
    */
	public String getId();
   
   /**
   *
   * Returns the object bound with the specified name in this session, or
   * <code>null</code> if no object is bound under the name.
   *
   * @param name		a string specifying the name of the object
   *
   * @return			the object with the specified name
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
   public Object getAttribute(String name);

   /**
   *
   * Returns an <code>Enumeration</code> of <code>String</code> objects
   * containing the names of all the objects bound to this session. 
   *
   * @return			an <code>Enumeration</code> of 
   *				<code>String</code> objects specifying the
   *				names of all the objects bound to
   *				this session
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public Enumeration<?> getAttributeNames();
  
  /**
   * Binds an object to this session, using the name specified.
   * If an object of the same name is already bound to the session,
   * the object is replaced.
   *
   * <p>After this method executes, and if the object
   * implements <code>HttpSessionBindingListener</code>,
   * the container calls 
   * <code>HttpSessionBindingListener.valueBound</code>.
   *
   * @param name			the name to which the object is bound;
   *					cannot be null
   *
   * @param value			the object to be bound; cannot be null
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public void setAttribute(String name, Object value);
  
  /**
  *
  * Removes the object bound with the specified name from
  * this session. If the session does not have an object
  * bound with the specified name, this method does nothing.
  *
  * <p>After this method executes, and if the object
  * implements <code>HttpSessionBindingListener</code>,
  * the container calls 
  * <code>HttpSessionBindingListener.valueUnbound</code>.
  * 
  * 
  *
  * @param name				the name of the object to
  *						remove from this session
  *
  * @exception IllegalStateException	if this method is called on an
  *					invalidated session
  */
  public void removeAttribute(String name);
 
 /**
  * Invalidates this session then unbinds any objects bound
  * to it. 
  *
  * @exception IllegalStateException	if this method is called on an
  *					already invalidated session
  */
  public void invalidate();
	
}
