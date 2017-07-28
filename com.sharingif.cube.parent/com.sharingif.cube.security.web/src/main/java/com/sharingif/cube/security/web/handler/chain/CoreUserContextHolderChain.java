package com.sharingif.cube.security.web.handler.chain;

import com.sharingif.cube.communication.http.HttpRequest;
import com.sharingif.cube.communication.http.HttpResponse;
import com.sharingif.cube.communication.http.request.HttpRequestInfo;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.user.CoreUserContextHolder;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.web.user.IWebUserManage;

public class CoreUserContextHolderChain extends AbstractHandlerMethodChain{
	
	private IWebUserManage webUserManage;
	
	public IWebUserManage getWebUserManage() {
		return webUserManage;
	}
	public void setWebUserManage(IWebUserManage webUserManage) {
		this.webUserManage = webUserManage;
	}
	
	@Override
	public void before(HandlerMethodContent content) throws CubeException {
		HttpRequestInfo<HttpRequest,HttpResponse> httpRequestInfo = content.getRequestInfo();
		
		ICoreUser coreUser = webUserManage.getUser(httpRequestInfo.getRequest());
		if(coreUser == null)
			return;
		
		CoreUserContextHolder.setContext(coreUser);
	}
	@Override
	public void after(HandlerMethodContent content)
			throws CubeException {
		CoreUserContextHolder.clearContext();
	}
	
	
}
