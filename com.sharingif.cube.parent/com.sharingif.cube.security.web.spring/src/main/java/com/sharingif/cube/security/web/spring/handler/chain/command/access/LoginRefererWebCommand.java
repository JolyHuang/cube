package com.sharingif.cube.security.web.spring.handler.chain.command.access;


import org.springframework.web.servlet.ModelAndView;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.security.web.access.INoUserHandler;
import com.sharingif.cube.web.springmvc.request.SpringMVCHttpRequestContext;

/**
 *
 * @Description:  [登录引用回跳]
 * @Author:       [Joly]
 * @CreateDate:   [2014年8月22日 下午5:11:31]
 * @UpdateUser:   [Joly]
 * @UpdateDate:   [2014年8月22日 下午5:11:31]
 * @UpdateRemark: [说明本次修改内容]
 * @Version:      [v1.0]
 *
 */
public class LoginRefererWebCommand extends AbstractHandlerMethodCommand{
	
	private INoUserHandler noUserHandler;
	
	public INoUserHandler getNoUserHandler() {
		return noUserHandler;
	}
	public void setNoUserHandler(INoUserHandler noUserHandler) {
		this.noUserHandler = noUserHandler;
	}

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		Object returnValue = content.getReturnValue();
		if(!(returnValue instanceof ModelAndView)){
			return;
		}
		
		ModelAndView modelAndView = (ModelAndView)returnValue;
		SpringMVCHttpRequestContext httpRequestContext = content.getRequestContext();
		String referer = noUserHandler.handleReferer(httpRequestContext.getRequest());
		if(!StringUtils.isEmpty(referer)){
			modelAndView.setViewName(referer);
		}
		
	}

}
