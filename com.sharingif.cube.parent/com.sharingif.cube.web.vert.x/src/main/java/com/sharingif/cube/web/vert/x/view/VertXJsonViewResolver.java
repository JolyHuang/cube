package com.sharingif.cube.web.vert.x.view;

import org.springframework.http.MediaType;

import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.core.request.RequestInfo;
import com.sharingif.cube.web.exception.handler.WebExceptionContent;

import io.vertx.ext.web.RoutingContext;

/**
 * ContentNegotiatingViewResolver
 * 2016年12月28日 下午3:44:29
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXJsonViewResolver implements ViewResolver<RoutingContext, WebExceptionContent> {

	private View<RoutingContext, WebExceptionContent> view = new VertXJsonView();
	
	@Override
	public View<RoutingContext, WebExceptionContent> resolveView(RequestInfo<RoutingContext> requestInfo, Object returnValue, WebExceptionContent exceptionContent) {
		MediaType candidateContentType = MediaType.parseMediaType(requestInfo.getMediaType());
		if(MediaType.APPLICATION_JSON.isCompatibleWith(candidateContentType)) {
			return view;
		} else {
			return null;
		}
		
	}

}
