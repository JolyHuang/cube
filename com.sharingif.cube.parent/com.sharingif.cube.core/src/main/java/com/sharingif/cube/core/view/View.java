package com.sharingif.cube.core.view;

import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;

/**
 * View
 * 2016年12月28日 下午3:20:57
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface View {
	
	void view(RequestContext<?> requestContext, Object returnValue,  ExceptionContent exceptionContent);
	
}
