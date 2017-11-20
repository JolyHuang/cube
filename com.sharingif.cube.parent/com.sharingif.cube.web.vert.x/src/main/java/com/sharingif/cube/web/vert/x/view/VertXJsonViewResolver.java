package com.sharingif.cube.web.vert.x.view;


import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.view.View;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;

/**
 * ContentNegotiatingViewResolver
 * 2016年12月28日 下午3:44:29
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class VertXJsonViewResolver implements ViewResolver {

	private View view = new VertXJsonView();
	
	@Override
	public View resolveView(RequestContext<?> requestContext, Object returnValue, ExceptionContent exceptionContent) {
		if(requestContext.getMediaType() == null) {
			return null;
		}

		MediaType candidateContentType = MediaType.parseMediaType(requestContext.getMediaType());
		if(MediaType.APPLICATION_JSON.isCompatibleWith(candidateContentType)) {
			return view;
		} else {
			return null;
		}
		
	}

}
