package com.sharingif.cube.security.handler.chain.command.access;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.CoreUserContextHolder;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.security.access.authority.IAuthorityAccessDecisionHandler;
import com.sharingif.cube.security.authentication.authority.IAuthorityRepertory;

/**   
 *  
 * @Description:  [处理权限访问]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月21日 下午12:55:22]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月21日 下午12:55:22]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class AuthorityAccessDecisionCommand extends AbstractHandlerMethodCommand {
	
	private String replaceContent;
	
	public String getReplaceContent() {
		return replaceContent;
	}
	public void setReplaceContent(String replaceContent) {
		this.replaceContent = replaceContent;
	}
	
	private IAuthorityAccessDecisionHandler authorityAccessDecisionHandler;
	
	public IAuthorityAccessDecisionHandler getAuthorityAccessDecisionHandler() {
		return authorityAccessDecisionHandler;
	}
	public void setAuthorityAccessDecisionHandler(IAuthorityAccessDecisionHandler  authorityAccessDecisionHandler) {
		this.authorityAccessDecisionHandler = authorityAccessDecisionHandler;
	}

	@Override
	public void execute(HandlerMethodContent content) throws CubeException {
		ICoreUser coreUser = getCoreUser();
		authorityAccessDecisionHandler.handleAuthorityAccessDecision((IAuthorityRepertory<?>)coreUser, StringUtils.replace(getAuthorityCode(content), replaceContent, ""));
	}
	
	protected ICoreUser getCoreUser() {
		return CoreUserContextHolder.getContext();
	}
	
	protected String getAuthorityCode(HandlerMethodContent content) {
		return (new StringBuilder().append(content.getObj().getClass().getName()).append(".").append(content.getMethod().getName())).toString();
	}
	
}
