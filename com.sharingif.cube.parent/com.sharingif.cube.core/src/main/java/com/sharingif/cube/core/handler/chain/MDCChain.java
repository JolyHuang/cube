package com.sharingif.cube.core.handler.chain;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.MDC;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
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
public class MDCChain extends AbstractHandlerMethodChain<HandlerMethodContent> {

	@Override
	public void before(HandlerMethodContent handlerMethodContent) throws CubeException {
		Map<String, String> mdcVariables = new HashMap<String, String>();
		
		ICoreUser coreUser = CoreUserContextHolder.getContext();
		String uniqueId = (coreUser == null ? "NOUSER" : coreUser.getUniqueId());
		mdcVariables.put("userId", uniqueId);
		
		String transUniqueId = RequestLocalContextHolder.getToken();
		transUniqueId = (transUniqueId == null ? "" : transUniqueId);
		mdcVariables.put("transUniqueId", transUniqueId);
		
		MDC.setContextMap(mdcVariables);
	}

	@Override
	public void after(HandlerMethodContent handlerMethodContent) throws CubeException {
		MDC.clear();
	}

	@Override
	public void exception(HandlerMethodContent handlerMethodContent, Exception exception) throws CubeException {
		MDC.clear();
	}

	
	
}
