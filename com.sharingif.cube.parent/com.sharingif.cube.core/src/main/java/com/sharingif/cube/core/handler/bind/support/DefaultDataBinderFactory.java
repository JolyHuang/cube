package com.sharingif.cube.core.handler.bind.support;

import org.springframework.validation.DataBinder;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.request.RequestContext;



/**
 * Create a {@link WebRequestDataBinder} instance and initialize it with a
 * {@link BindingInitializer}.
 * 2015年7月26日 下午8:08:24
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class DefaultDataBinderFactory implements DataBinderFactory {
	
	private final BindingInitializer initializer;
	
	/**
	 * Create new instance.
	 * @param initializer for global data binder intialization, or {@code null}
	 */
	public DefaultDataBinderFactory(BindingInitializer initializer) {
		this.initializer = initializer;
	}

	/**
	 * Create a new {@link DataBinder} for the given target object and
	 * initialize it through a {@link WebBindingInitializer}.
	 * @throws Exception in case of invalid state or arguments
	 */
	@Override
	public final DataBinder createBinder(RequestContext<?> requestContext, Object target, String objectName) throws CubeException {
		DataBinder dataBinder = createBinderInstance(target, objectName, requestContext);
		if (this.initializer != null) {
			this.initializer.initBinder(dataBinder, requestContext);
		}
		initBinder(dataBinder, requestContext);
		return dataBinder;
	}

	/**
	 * Extension point to create the WebDataBinder instance.
	 * By default this is {@code WebRequestDataBinder}.
	 * @param target the binding target or {@code null} for type conversion only
	 * @param objectName the binding target object name
	 * @param webRequest the current request
	 * @throws Exception in case of invalid state or arguments
	 */
	protected DataBinder createBinderInstance(Object target, String objectName, RequestContext<?> requestContext) throws CubeException {
		return new DataBinder(target, objectName);
	}

	/**
	 * Extension point to further initialize the created data binder instance
	 * (e.g. with {@code @InitBinder} methods) after "global" initializaton
	 * via {@link WebBindingInitializer}.
	 * @param dataBinder the data binder instance to customize
	 * @param webRequest the current request
	 * @throws Exception if initialization fails
	 */
	protected void initBinder(DataBinder dataBinder, RequestContext<?> requestContext) throws CubeException {
	}

}
