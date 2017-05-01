package com.sharingif.cube.security.web.handler.chain;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.user.CoreUserContextHolder;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.web.user.IWebUserManage;

public class CoreUserContextHolderChain extends AbstractHandlerMethodChain<HttpHandlerMethodContent>{
	
	private IWebUserManage webUserManage;
	
	public IWebUserManage getWebUserManage() {
		return webUserManage;
	}
	public void setWebUserManage(IWebUserManage webUserManage) {
		this.webUserManage = webUserManage;
	}
	
	@Override
	public void before(HttpHandlerMethodContent content)
			throws CubeException {
		ICoreUser coreUser = webUserManage.getUser(content.getRequest());
		if(coreUser == null)
			return;
		
		CoreUserContextHolder.setContext(coreUser);
	}
	@Override
	public void after(HttpHandlerMethodContent content)
			throws CubeException {
		CoreUserContextHolder.clearContext();
	}
	
	
}
