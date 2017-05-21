package com.sharingif.cube.web.vert.x.view;


import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestInfo;
import io.vertx.ext.web.RoutingContext;

/**
 * ContentNegotiatingViewResolver
 * 2016年12月28日 下午3:44:29
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXJsonViewResolver implements ViewResolver<RoutingContext> {

	private View<RoutingContext> view = new VertXJsonView();
	
	@Override
	public View<RoutingContext> resolveView(RequestInfo<RoutingContext> requestInfo, Object returnValue, ExceptionContent exceptionContent) {
		MediaType candidateContentType = MediaType.parseMediaType(requestInfo.getMediaType());
		if(MediaType.APPLICATION_JSON.isCompatibleWith(candidateContentType)) {
			return view;
		} else {
			return null;
		}
		
	}

}
