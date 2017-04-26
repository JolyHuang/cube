package com.sharingif.cube.communication.view;

import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * ViewResolver
 * 2016年12月28日 下午3:18:46
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface ViewResolver<T, O extends ExceptionContent> {
	
	View<T,O> resolveView(RequestInfo<T> requestInfo, Object returnValue, O exceptionContent);

}
