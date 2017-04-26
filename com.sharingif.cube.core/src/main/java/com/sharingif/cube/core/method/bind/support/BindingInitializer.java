package com.sharingif.cube.core.method.bind.support;

import org.springframework.validation.DataBinder;

import com.sharingif.cube.core.request.RequestInfo;


/**
 * Callback interface for initializing a {@link org.springframework.validation.DataBinder}
 * for performing data binding in the context of a specific web request.
 * 2015年7月26日 下午8:10:37
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface BindingInitializer {
	
	/**
	 * Initialize the given DataBinder for the given request.
	 * @param binder the DataBinder to initialize
	 * @param request the web request that the data binding happens within
	 */
	void initBinder(DataBinder binder, RequestInfo<?> requestInfo);

}
