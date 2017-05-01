package com.sharingif.cube.core.handler.bind.support;

import org.springframework.validation.DataBinder;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.request.RequestInfo;



/**
 * A factory for creating a {@link DataBinder} instance for a named target object.
 * 2015年7月26日 下午8:05:33
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public interface DataBinderFactory {
	
	/**
	 * Create a {@link DataBinder} for the given object.
	 * @param webRequest the current request
	 * @param target the object to create a data binder for, or {@code null} if creating a binder for a simple type
	 * @param objectName the name of the target object
	 * @return the created {@link DataBinder} instance, never null
	 * @throws Exception raised if the creation and initialization of the data binder fails
	 */
	DataBinder createBinder(RequestInfo<?> requestInfo, Object target, String objectName) throws CubeException;

}
