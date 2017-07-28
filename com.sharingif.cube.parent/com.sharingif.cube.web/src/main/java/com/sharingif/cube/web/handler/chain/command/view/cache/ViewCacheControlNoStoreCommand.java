package com.sharingif.cube.web.handler.chain.command.view.cache;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestInfo;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.web.servlet.view.ViewCacheControl;

/**
 * 返回视图无缓存
 * 2015年11月22日 下午12:32:12
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class ViewCacheControlNoStoreCommand extends AbstractHandlerMethodCommand {

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		HttpRequestInfo<HttpRequest,HttpResponse> httpRequestInfo = content.getRequestInfo();
		
		httpRequestInfo.getRequest().setAttribute(ViewCacheControl.VIEW_CACHE_CONTROL_TYPE, ViewCacheControl.NO_STORE);
	}

}
