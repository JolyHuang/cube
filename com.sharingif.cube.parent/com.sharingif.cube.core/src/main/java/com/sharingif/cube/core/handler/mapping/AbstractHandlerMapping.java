package com.sharingif.cube.core.handler.mapping;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.request.RequestInfo;


/**
 * AbstractHandlerMapping
 * 2015年6月28日 下午7:08:15]
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public abstract class AbstractHandlerMapping<RI,T> implements HandlerMapping<RI,T>, ApplicationContextAware {
	
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	private ApplicationContext applicationContext;
	
	public void setPathMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher must not be null");
		this.pathMatcher = pathMatcher;
	}
	
	public PathMatcher getPathMatcher() {
		return this.pathMatcher;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	protected abstract T getHandlerInternal(RequestInfo<RI> request) throws CubeException;
	
	@Override
	public T getHandler(RequestInfo<RI> request) throws CubeException {
		return getHandlerInternal(request);
	}
	
}
