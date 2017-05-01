package com.sharingif.cube.web.method.chain.slf4j;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.MDC;

import com.sharingif.cube.communication.http.method.HttpHandlerMethodContent;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.request.RequestLocalContextHolder;
import com.sharingif.cube.core.user.CoreUserContextHolder;
import com.sharingif.cube.core.user.ICoreUser;

/**
 *
 * @Description:  [给日志添加变量]
 * @Author:       [Joly]
 * @CreateDate:   [2014年6月14日 下午5:33:13]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年6月14日 下午5:33:13]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class MDCChain extends AbstractHandlerMethodChain<HttpHandlerMethodContent> {

	@Override
	public void before(HttpHandlerMethodContent handlerMethodContent) throws CubeException {
		Map<String, String> mdcVariables = new HashMap<String, String>();
		
		ICoreUser coreUser = CoreUserContextHolder.getContext();
		String uniqueId = (coreUser == null ? "NOUSER" : coreUser.getUniqueId());
		
		String sessionId = null;
		if(handlerMethodContent.getRequest() == null || handlerMethodContent.getRequest().getSession()==null || handlerMethodContent.getRequest().getSession().getId() == null) {
			sessionId = "NOSESSIONID";
		} else {
			sessionId = handlerMethodContent.getRequest().getSession().getId();
		}
		
		String requestUniqueId = (RequestLocalContextHolder.getToken() == null ? "NOUNIQUENUMBER" : RequestLocalContextHolder.getToken());
		
		mdcVariables.put("userId", uniqueId);
		mdcVariables.put("sessionId", sessionId);
		mdcVariables.put("requestUniqueId", requestUniqueId);
		
		MDC.setContextMap(mdcVariables);
	}

	@Override
	public void after(HttpHandlerMethodContent handlerMethodContent) throws CubeException {
		MDC.clear();
	}

	@Override
	public void exception(HttpHandlerMethodContent handlerMethodContent, Exception exception) {
		super.exception(handlerMethodContent, exception);
		MDC.clear();
	}

	
	
}
