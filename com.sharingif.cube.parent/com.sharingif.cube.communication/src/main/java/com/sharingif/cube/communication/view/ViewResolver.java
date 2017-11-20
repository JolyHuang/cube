package com.sharingif.cube.communication.view;

import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;

/**
 * ViewResolver
 * 2016年12月28日 下午3:18:46
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface ViewResolver {
	
	View resolveView(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent);

}
