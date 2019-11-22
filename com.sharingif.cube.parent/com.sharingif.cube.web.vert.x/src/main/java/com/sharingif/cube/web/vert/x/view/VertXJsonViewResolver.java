package com.sharingif.cube.web.vert.x.view;


import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.core.view.View;
import com.sharingif.cube.core.view.ViewResolver;
import com.sharingif.cube.core.exception.handler.ExceptionContent;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.web.vert.x.http.VertXHttpRequest;
import io.vertx.core.http.HttpHeaders;

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
		VertXHttpRequest vertXHttpRequest = (VertXHttpRequest) requestContext.getRequest();

		String mediaType = vertXHttpRequest.getHttpServerRequest().headers().get(HttpHeaders.ACCEPT);

		if(StringUtils.isTrimEmpty(mediaType)) {
			return null;
		}

		MediaType candidateContentType = MediaType.parseMediaType(mediaType);
		if(MediaType.APPLICATION_JSON.isCompatibleWith(candidateContentType)) {
			return view;
		} else {
			return null;
		}
		
	}

}
