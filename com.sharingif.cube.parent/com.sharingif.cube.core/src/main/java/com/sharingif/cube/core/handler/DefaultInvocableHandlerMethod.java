package com.sharingif.cube.core.handler;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.adapter.HandlerMethodReturnValueHandler;
import com.sharingif.cube.core.handler.adapter.HandlerMethodReturnValueHandlerComposite;
import com.sharingif.cube.core.request.RequestContext;

/**
 * 默认InvocableHandlerMethod
 * 2015年7月20日 下午10:47:34
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class DefaultInvocableHandlerMethod extends InvocableHandlerMethod {
	
	private HandlerMethodReturnValueHandlerComposite returnValueHandlers;
	
	public DefaultInvocableHandlerMethod(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}
	
	/**
	 * Register {@link HandlerMethodReturnValueHandler} instances to use to
	 * handle return values.
	 */
	public void setHandlerMethodReturnValueHandlers(HandlerMethodReturnValueHandlerComposite returnValueHandlers) {
		this.returnValueHandlers = returnValueHandlers;
	}
	
	/**
	 * Invokes the method and handles the return value through one of the
	 * configured {@link HandlerMethodReturnValueHandler}s.
	 * @param webRequest the current request
	 * @param mavContainer the ModelAndViewContainer for this request
	 * @param providedArgs "given" arguments matched by type (not resolved)
	 */
	public Object invokeAndHandle(RequestContext<?> requestContext) throws CubeException {
		
		Object returnValue = invokeForRequest(requestContext);
		
		if(returnValue == null)
			return null;
			
		try {
			if(returnValueHandlers == null) {
				return returnValue;
			} else {
				return this.returnValueHandlers.handleReturnValue(requestContext, returnValue, getReturnValueType(returnValue), getDataBinderFactory());
			}
		} catch (CubeException ex) {
			if (logger.isTraceEnabled()) {
				logger.trace(getReturnValueHandlingErrorMessage("Error handling return value", returnValue), ex);
			}
			throw ex;
		}
	}
	
	private String getReturnValueHandlingErrorMessage(String message, Object returnValue) {
		StringBuilder sb = new StringBuilder(message);
		if (returnValue != null) {
			sb.append(" [type=").append(returnValue.getClass().getName()).append("]");
		}
		sb.append(" [value=").append(returnValue).append("]");
		return getDetailedErrorMessage(sb.toString());
	}

}
