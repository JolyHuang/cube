package com.sharingif.cube.security.access.authority;

import com.sharingif.cube.security.authentication.authority.IAuthorityHandler;
import com.sharingif.cube.security.authentication.authority.IAuthorityRepertory;
import com.sharingif.cube.security.exception.validation.access.AccessDecisionCubeException;

/**   
 *  
 * @Description:  [处理权限访问]   
 * @Author:       [Joly_Huang]   
 * @CreateDate:   [2014年3月21日 下午1:33:10]   
 * @UpdateUser:   [Joly_Huang]   
 * @UpdateDate:   [2014年3月21日 下午1:33:10]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0] 
 *    
 */
public class AuthorityAccessDecisionHandler implements IAuthorityAccessDecisionHandler {
	
	private IAuthorityHandler<Object, Object> authorityHandler;
	
	public IAuthorityHandler<Object, Object> getAuthorityHandler() {
		return authorityHandler;
	}
	public void setAuthorityHandler(
			IAuthorityHandler<Object, Object> authorityHandler) {
		this.authorityHandler = authorityHandler;
	}

	@Override
	public void handleAuthorityAccessDecision(IAuthorityRepertory<?> authorityRepertory, String authorityCode) throws AccessDecisionCubeException {
		if(!authorityHandler.authenAuthority(authorityRepertory.getAuthorities(), authorityCode)){
			throw newAccessDecisionCubeException(authorityCode);
		}
	}
	
	protected AccessDecisionCubeException newAccessDecisionCubeException(String authorityCode){
		return new AccessDecisionCubeException("user has not enough rights", new String[]{authorityCode});
	}

}
