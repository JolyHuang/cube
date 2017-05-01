package com.sharingif.cube.security.web.spring.handler.chain.command.access;


import org.springframework.web.servlet.ModelAndView;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.security.web.access.INoUserHandler;
import com.sharingif.cube.web.springmvc.handler.SpringMVCHandlerMethodContent;
import com.sharingif.cube.web.springmvc.handler.chain.command.AbstractSpringMVCHandlerMethodCommand;

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
public class LoginRefererWebCommand extends AbstractSpringMVCHandlerMethodCommand{
	
	private INoUserHandler noUserHandler;
	
	public INoUserHandler getNoUserHandler() {
		return noUserHandler;
	}
	public void setNoUserHandler(INoUserHandler noUserHandler) {
		this.noUserHandler = noUserHandler;
	}

	@Override
	public void execute(SpringMVCHandlerMethodContent content) throws CubeException {
		Object returnValue = content.getReturnValue();
		if(!(returnValue instanceof ModelAndView)){
			return;
		}
		
		ModelAndView modelAndView = (ModelAndView)returnValue;
		String referer = noUserHandler.handleReferer(content.getRequest());
		if(!StringUtils.isEmpty(referer)){
			modelAndView.setViewName(referer);
		}
		
	}

}
