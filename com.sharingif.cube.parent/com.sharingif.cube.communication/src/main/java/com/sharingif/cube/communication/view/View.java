package com.sharingif.cube.communication.view;

import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * View
 * 2016年12月28日 下午3:20:57
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface View<T> {
	
	void view(RequestInfo<T> requestInfo, Object returnValue,  ExceptionContent exceptionContent);
	
}
