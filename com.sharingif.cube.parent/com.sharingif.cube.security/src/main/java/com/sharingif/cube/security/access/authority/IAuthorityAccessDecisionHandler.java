package com.sharingif.cube.security.access.authority;

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
public interface IAuthorityAccessDecisionHandler {
	
	/**
	 * 处理权限访问
	 * @param securityUser
	 * @param authorityCode
	 * @throws AccessDecisionCubeException
	 */
	void handleAuthorityAccessDecision(IAuthorityRepertory<?> authorityRepertory,String authorityCode) throws AccessDecisionCubeException;
	
}
